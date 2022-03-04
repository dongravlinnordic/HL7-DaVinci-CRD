package org.hl7.davinci.endpoint.components;

import org.cdshooks.CdsRequest;
import org.hl7.davinci.FatalRequestIncompleteException;
import org.hl7.davinci.FhirComponentsT;
import org.hl7.davinci.endpoint.cdshooks.services.crd.CdsService;
import org.hl7.davinci.endpoint.cdshooks.services.crd.r4.FhirRequestProcessor;
import org.hl7.davinci.r4.crdhook.CrdPrefetch;
import org.hl7.davinci.r4.crdhook.ServiceContext;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleEntryRequestComponent;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Coverage;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.Bundle.HTTPVerb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ca.uhn.fhir.context.FhirContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class QueryBatchRequest {

  private static final Logger logger = LoggerFactory.getLogger(QueryBatchRequest.class);
  private static final String PRACTIONER_ROLE = "PractitionerRole";

  // private final CdsService<?> cdsService;
  private final CdsRequest<CrdPrefetch, ServiceContext> cdsRequest;
  private final FhirComponentsT fhirComponents;

  public QueryBatchRequest(CdsService<?> cdsService, CdsRequest<?, ?> cdsRequest, FhirComponentsT fhirComponents) {
    // A Query Batch Request is used to try and populate missing fields that the
    // prefetch/hydrator missed.
    // this.cdsService = cdsService;
    this.cdsRequest = (CdsRequest<CrdPrefetch, ServiceContext>) cdsRequest;
    this.fhirComponents = fhirComponents;
  }

  /**
   * Backfills the missing required values of the response. In this case, it
   * should be called after the Prefetch and Prefetch Hydrator have run,
   * querying and adding the attributes they missed.
   * Approach:
   * 1. Pull the IDs of the required references from the request object's draft
   * orders.
   * 2. See which of those values are missing from the current CRD response.
   * 3. Build the Query Batch JSON request using
   * http://build.fhir.org/ig/HL7/davinci-crd/hooks.html#fhir-resource-access
   * 4. Populate the CRD response with the values from the Query Batch response.
   */
  public void performQueryBatchRequest() {
    logger.info("***** ***** Attempting Query Batch Request.");

    CrdPrefetch crdResponse = cdsRequest.getPrefetch();
    // The list of references that should be queried in the batch request.
    List<String> requiredReferences = new ArrayList<String>();

    // Get the IDs of references in the request's draft orders.
    Bundle draftOrdersBundle = cdsRequest.getContext().getDraftOrders();
    // This assumes that only the first draft order is relevant.
    Resource initialRequestResource = draftOrdersBundle.getEntry().get(0).getResource();
    ResourceType requestType = initialRequestResource.getResourceType();
    // Extract the references by iterating through the JSON.
    Gson gson = new Gson();
    final JsonObject jsonObject = gson.toJsonTree(initialRequestResource).getAsJsonObject();
    for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
      FhirRequestProcessor.extractReferenceIds(requiredReferences, entry.getValue());
    }

    // Filter out references that already exist in the CRD Response.
    requiredReferences = requiredReferences.stream()
        .filter(referenceId -> !crdResponse.containsRequestResourceId(referenceId))
        .collect(Collectors.toList());

    logger.info("References to query: " + requiredReferences);
    if (requiredReferences.isEmpty()) {
      logger.info("A Query Batch Request is not needed: all references have already already fetched.");
      return;
    }

    // Build the Query Batch Request JSON.
    Bundle queryBatchRequestBundle = buildQueryBatchRequestBundle(requiredReferences);
    String queryBatchRequest = FhirContext.forR4().newJsonParser().encodeResourceToString(queryBatchRequestBundle);

    // Make the query batch request to the EHR server.
    Bundle queryResponseBundle = null;
    try {
      logger.info("Executing Query Batch Request: " + queryBatchRequest);
      queryResponseBundle = (Bundle) executeFhirQuery(queryBatchRequest, this.cdsRequest, this.fhirComponents);
      queryResponseBundle = extractNestedBundledResources(queryResponseBundle);
      logger.info("Extracted Query Batch Resources: "
          + (queryResponseBundle).getEntry().stream().map(entry -> entry.getResource()).collect(Collectors.toList()));
    } catch (Exception e) {
      logger.error("Failed to backfill prefetch with Query Batch Request " + queryBatchRequest, e);
    }

    if (queryResponseBundle == null) {
      logger.error("No response recieved from the Query Batch Request.");
      return;
    }

    // Add the request resource to the query batch response as it may be missing.
    // Coverage and Subject are not automatically being
    // linked to the request object. It seems to somehow automatically link during
    // standard prefetch, but not here so we're doing it manually.
    List<Coverage> coverages = FhirRequestProcessor.extractCoverageFromBundle(queryResponseBundle);
    List<Patient> patients = FhirRequestProcessor.extractPatientsFromBundle(queryResponseBundle);
    FhirRequestProcessor.addInsuranceAndSubject(initialRequestResource, patients, coverages);
    BundleEntryComponent newEntry = new BundleEntryComponent();
    newEntry.setResource(initialRequestResource);
    queryResponseBundle.addEntry(newEntry);

    // Add the query batch response resources to the CRD Prefetch request.
    logger.info("Query Batch Response Entries: " + queryResponseBundle.getEntry());
    FhirRequestProcessor.addToCrdPrefetchRequest(crdResponse, requestType, queryResponseBundle.getEntry());
    logger.info("Post-Query Batch CRDResponse: " + crdResponse);
  }

  /**
   * Executes the given fhir query.
   * 
   * @param query
   * @param cdsRequest
   * @param fhirComponents
   * @return
   */
  private static IBaseResource executeFhirQuery(String query, CdsRequest<?, ?> cdsRequest,
      FhirComponentsT fhirComponents) {
    if (cdsRequest.getFhirServer() == null) {
      throw new FatalRequestIncompleteException("Attempted to perform a Query Batch Request, but no fhir "
          + "server provided.");
    }
    // Remove the trailing '/' if there is one.
    String fhirBase = cdsRequest.getFhirServer();
    if (fhirBase != null && fhirBase.endsWith("/")) {
      fhirBase = fhirBase.substring(0, fhirBase.length() - 1);
    }
    String fullUrl = fhirBase + "/";

    String token = null;
    if (cdsRequest.getFhirAuthorization() != null) {
      token = cdsRequest.getFhirAuthorization().getAccessToken();
    }

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    if (token != null) {
      headers.set("Authorization", "Bearer " + token);
    }
    HttpEntity<String> entity = new HttpEntity<>(query, headers);
    try {
      logger.info("Fetching: " + fullUrl);
      // Request source: https://www.hl7.org/fhir/http.html#transaction
      ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.POST, entity, String.class);
      logger.info("Fetched: " + response.getBody());
      return fhirComponents.getJsonParser().parseResource(response.getBody());
    } catch (RestClientException e) {
      logger.warn("Unable to make the fetch request", e);
      return null;
    }
  }

  /**
   * Builds a query batch request bundle based on the given references to request.
   * 
   * @param resourceReferences
   * @return
   */
  private static Bundle buildQueryBatchRequestBundle(List<String> resourceReferences) {
    // http://build.fhir.org/ig/HL7/davinci-crd/hooks.html#fhir-resource-access
    Bundle queryBatchBundle = new Bundle();
    queryBatchBundle.setType(BundleType.BATCH);
    for (String reference : resourceReferences) {
      if (reference.contains(PRACTIONER_ROLE)) {
        reference = QueryBatchRequest.buildPractionerRoleQuery(reference);
      }
      BundleEntryComponent entry = new BundleEntryComponent();
      BundleEntryRequestComponent request = new BundleEntryRequestComponent();
      request.setMethod(HTTPVerb.GET);
      request.setUrl(reference);
      entry.setRequest(request);
      queryBatchBundle.addEntry(entry);
    }
    return queryBatchBundle;
  }

  /**
   * Adds support for PractitionerRole nested requests.
   * 
   * @param reference
   * @return
   */
  private static String buildPractionerRoleQuery(String reference) {
    String[] referenceIdSplit = reference.split("/");
    String referenceId = referenceIdSplit[referenceIdSplit.length - 1];
    String query = "PractitionerRole?_id=" + referenceId
        + "&_include=PractitionerRole:organization&_include=PractitionerRole:practitioner&_include=PractitionerRole:location";
    return query;
  }

  /**
   * Extracts the resources inside a bundled bundle to be at the top level of the
   * bundle, making them no longer nested.
   * 
   * @param resource
   * @return
   */
  private static Bundle extractNestedBundledResources(Bundle bundle) {
    List<BundleEntryComponent> entriesToAdd = new ArrayList<>();
    List<BundleEntryComponent> entriesToRemove = new ArrayList<>();
    for (int bundleIndex = 0; bundleIndex < bundle.getEntry().size(); bundleIndex++) {
      BundleEntryComponent entry = bundle.getEntry().get(bundleIndex);
      if (entry.getResource().getResourceType().equals(ResourceType.Bundle)) {
        Bundle bundledBundle = (Bundle) entry.getResource();
        entriesToAdd.addAll(bundledBundle.getEntry());
        entriesToRemove.add(entry);
      }
    }
    bundle.getEntry().addAll(entriesToAdd);
    bundle.getEntry().removeAll(entriesToRemove);
    return bundle;
  }

}

package org.hl7.davinci.endpoint.cdshooks.services.crd.r4;

import org.hl7.davinci.RequestIncompleteException;
import org.hl7.davinci.endpoint.cql.CqlRule;
import org.hl7.davinci.endpoint.cql.r4.CqlExecutionContextBuilder;
import org.hl7.davinci.endpoint.database.RuleMapping;
import org.hl7.davinci.endpoint.files.FileStore;
import org.hl7.davinci.endpoint.rules.CoverageRequirementRuleCriteria;
import org.hl7.davinci.endpoint.rules.CoverageRequirementRuleResult;
import org.hl7.davinci.r4.JacksonPrefetchDeserializer;
import org.hl7.davinci.r4.Utilities;
import org.hl7.davinci.r4.crdhook.CrdPrefetch;
import org.hl7.fhir.r4.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class FhirBundleProcessor {
  static final Logger logger = LoggerFactory.getLogger(FhirBundleProcessor.class);

  private FileStore fileStore;
  @JsonDeserialize(using = JacksonPrefetchDeserializer.class)
  private CrdPrefetch prefetch;
  private String baseUrl;
  private List<String> selections;
  private List<CoverageRequirementRuleResult> results = new ArrayList<>();


  public FhirBundleProcessor(CrdPrefetch prefetch, FileStore fileStore, String baseUrl, List<String> selections) {
    this.prefetch = prefetch;
    this.fileStore = fileStore;
    this.baseUrl = baseUrl;
    this.selections = selections;
  }

  public FhirBundleProcessor(CrdPrefetch prefetch, FileStore fileStore, String baseUrl) {
    this(prefetch, fileStore, baseUrl, new ArrayList<>());
  }

  public List<CoverageRequirementRuleResult> getResults() { return results; }

  public void processDeviceRequests() {
    Bundle deviceRequestBundle = prefetch.getDeviceRequestBundle();
    List<Organization> payorList = prefetch.getCoveragePayors();
    List<DeviceRequest> deviceRequestList = Utilities.getResourcesOfTypeFromBundle(DeviceRequest.class, deviceRequestBundle);
    List<Patient> patients = Utilities.getResourcesOfTypeFromBundle(Patient.class, deviceRequestBundle);
    logger.info("r4/FhirBundleProcessor::processDeviceRequests: Found " + patients.size() + " patients.");

    if (!deviceRequestList.isEmpty()) {
      logger.info("r4/FhirBundleProcessor::getAndProcessDeviceRequests: DeviceRequest(s) found");

      for (DeviceRequest deviceRequest : deviceRequestList) {
        if (idInSelectionsList(deviceRequest.getId())) {
          List<CoverageRequirementRuleCriteria> criteriaList = createCriteriaList(deviceRequest.getCodeCodeableConcept(), deviceRequest.getInsurance(), payorList);
          
          String patientReference = deviceRequest.getSubject().getReference();
          List<Patient> deviceRequestPrefetchedPatients = patients.stream().filter((patient) -> {
            String patientId = patient.getId();
            return patientId.contains(patientReference) || patientReference.contains(patientId);
          }).collect(Collectors.toList());

          logger.info("r4/FhirBundleProcessor::processDeviceRequests: DeviceRequest Subject - " + deviceRequest.getSubject());
          logger.info("r4/FhirBundleProcessor::processDeviceRequests: DeviceRequest Subject Resource - " + deviceRequest.getSubject().getResource());
          logger.info("r4/FhirBundleProcessor::processDeviceRequests: DeviceRequest Subject Reference - " + deviceRequest.getSubject().getReference());
          if (deviceRequestPrefetchedPatients.size() < 1) {
            logger.error("r4/FhirBundleProcessor::processDeviceRequests: ERROR - Device Request '" + deviceRequest.getId() + "'' does not contain a refernence to any of the prefetched patients. Device request contains patient reference '" + patientReference + "' and prefetch contains patients " + patients.stream().map(patient -> patient.getId()).collect(Collectors.toSet()) + ".");
          }
          Patient patientToUse = deviceRequestPrefetchedPatients.get(0);
          logger.info("r4/FhirBundleProcessor::processDeviceRequests: Found Patient '" + patientToUse + "'.");
          buildExecutionContexts(criteriaList, patientToUse, "device_request", deviceRequest);
        }
      }
    }
  }

  public void processMedicationRequests() {
    Bundle medicationRequestBundle = prefetch.getMedicationRequestBundle();
    List<Organization> payorList = prefetch.getCoveragePayors();
    List<MedicationRequest> medicationRequestList = Utilities.getResourcesOfTypeFromBundle(MedicationRequest.class, medicationRequestBundle);
    if (!medicationRequestList.isEmpty()) {
      logger.info("r4/FhirBundleProcessor::getAndProcessMedicationRequests: MedicationRequest(s) found");

      for (MedicationRequest medicationRequest : medicationRequestList) {
        if (idInSelectionsList(medicationRequest.getId())) {
          List<CoverageRequirementRuleCriteria> criteriaList = createCriteriaList(medicationRequest.getMedicationCodeableConcept(), medicationRequest.getInsurance(), payorList);
          buildExecutionContexts(criteriaList, (Patient) medicationRequest.getSubject().getResource(), "medication_request", medicationRequest);
        }
      }
    }
  }

  public void processMedicationDispenses() {
    Bundle medicationDispenseBundle = prefetch.getMedicationDispenseBundle();
    List<Organization> payorList = prefetch.getCoveragePayors();
    List<MedicationDispense> medicationDispenseList = Utilities.getResourcesOfTypeFromBundle(MedicationDispense.class, medicationDispenseBundle);
    List<Organization> medicationPayorList = Utilities.getResourcesOfTypeFromBundle(Organization.class,
        medicationDispenseBundle);
    payorList.addAll(medicationPayorList);
    if (!medicationDispenseList.isEmpty()) {
      logger.info("r4/FhirBundleProcessor::getAndProcessMedicationDispenses: MedicationDispense(s) found");

      for (MedicationDispense medicationDispense : medicationDispenseList) {
        if (idInSelectionsList(medicationDispense.getId())) {
          List<CoverageRequirementRuleCriteria> criteriaList = createCriteriaList(medicationDispense.getMedicationCodeableConcept(), null, payorList);
          buildExecutionContexts(criteriaList, (Patient) medicationDispense.getSubject().getResource(), "medication_dispense", medicationDispense);
        }
      }
    }
  }

  public void processServiceRequests() {
    Bundle serviceRequestBundle = prefetch.getServiceRequestBundle();
    List<Organization> payorList = prefetch.getCoveragePayors();
    List<ServiceRequest> serviceRequestList = Utilities.getResourcesOfTypeFromBundle(ServiceRequest.class, serviceRequestBundle);
    if (!serviceRequestList.isEmpty()) {
      logger.info("r4/FhirBundleProcessor::getAndProcessServiceRequests: ServiceRequest(s) found");

      for (ServiceRequest serviceRequest : serviceRequestList) {
        if (idInSelectionsList(serviceRequest.getId())) {
          List<CoverageRequirementRuleCriteria> criteriaList = createCriteriaList(serviceRequest.getCode(), serviceRequest.getInsurance(), payorList);
          buildExecutionContexts(criteriaList, (Patient) serviceRequest.getSubject().getResource(), "service_request", serviceRequest);
        }
      }
    }
  }

  public void processOrderSelectMedicationStatements() {
    Bundle medicationRequestBundle = prefetch.getMedicationRequestBundle();
    List<MedicationRequest> medicationRequestList = Utilities.getResourcesOfTypeFromBundle(MedicationRequest.class, medicationRequestBundle);

    Bundle medicationStatementBundle = prefetch.getMedicationStatementBundle();
    List<MedicationStatement> medicationStatementList = Utilities.getResourcesOfTypeFromBundle(MedicationStatement.class, medicationStatementBundle);

    List<Organization> payorList = prefetch.getCoveragePayors();

    if (!medicationRequestList.isEmpty()) {
      logger.info("r4/FhirBundleProcessor::processOrderSelectMedicationStatements: MedicationRequests(s) found");

      // process each of the MedicationRequests
      for (MedicationRequest medicationRequest : medicationRequestList) {
        if (idInSelectionsList(medicationRequest.getId())) {

          // run on each of the MedicationStatements
          for (MedicationStatement medicationStatement : medicationStatementList) {
            logger.info("r4/FhirBundleProcessor::processOrderSelectMedicationStatements: MedicationStatement found: " + medicationStatement.getId());

            List<CoverageRequirementRuleCriteria> criteriaList = createCriteriaList(medicationRequest.getMedicationCodeableConcept(), medicationRequest.getInsurance(), payorList);

            HashMap<String, Resource> cqlParams = new HashMap<>();
            cqlParams.put("Patient", (Patient) medicationRequest.getSubject().getResource());
            cqlParams.put("medication_request", medicationRequest);
            cqlParams.put("medication_statement", medicationStatement);

            buildExecutionContexts(criteriaList, cqlParams);
          }
        }
      }
    }
  }

  private List<CoverageRequirementRuleCriteria> createCriteriaList(CodeableConcept codeableConcept, List<Reference> insurance, List<Organization> payorList) {
    try {
      List<Coding> codings = codeableConcept.getCoding();
      if (codings.size() > 0) {
        logger.info("r4/FhirBundleProcessor::createCriteriaList: code[0]: " + codings.get(0).getCode() + " - " + codings.get(0).getSystem());
      } else {
        logger.info("r4/FhirBundleProcessor::createCriteriaList: empty codes list!");
      }

      List<Organization> payors = new ArrayList<>();
      if (insurance != null) {
        List<Coverage> coverages = insurance.stream()
            .map(reference -> (Coverage) reference.getResource()).collect(Collectors.toList());
        // Remove null coverages that may not have resolved.
        coverages = coverages.stream().filter(coverage -> coverage != null).collect(Collectors.toList());
        payors.addAll(Utilities.getPayors(coverages));
      }
      if (payorList != null) {
        payors.addAll(payorList);
      }

      if (payors.size() > 0) {
        logger.info("r4/FhirBundleProcessor::createCriteriaList: payer[0]: " + payors.get(0).getName());
      } else {
        // default to CMS if no payer was provided
        logger.info("r4/FhirBundleProcessor::createCriteriaList: empty payers list, working around by adding CMS!");
        Organization org = new Organization().setName("Centers for Medicare and Medicaid Services");
        org.setId("75f39025-65db-43c8-9127-693cdf75e712"); // how to get ID
        payors.add(org);
        // remove the exception to use CMS if no payer is provided
        // JIRA ticket https://jira.mitre.org/browse/DMEERX-894
        // throw new RequestIncompleteException("No Payer found in coverage resource, cannot find documentation.");
      }

      List<CoverageRequirementRuleCriteria> criteriaList = CoverageRequirementRuleCriteria
          .createQueriesFromR4(codings, payors);
      return criteriaList;
    } catch (RequestIncompleteException e) {
      // rethrow incomplete request exceptions
      throw e;
    } catch (Exception e) {
      // catch all remaining exceptions
      System.out.println(e);
      throw new RequestIncompleteException("Unable to parse list of codes, codesystems, and payors from a device request.");
    }
  }

  private void buildExecutionContexts(List<CoverageRequirementRuleCriteria> criteriaList, Patient patient, String requestType, DomainResource request) {
    System.out.println("buildExecutionContexts::PATIENT: " + patient);
    HashMap<String, Resource> cqlParams = new HashMap<>();
    cqlParams.put("Patient", patient);
    cqlParams.put(requestType, request);
    buildExecutionContexts(criteriaList, cqlParams);
  }

  private void buildExecutionContexts(List<CoverageRequirementRuleCriteria> criteriaList, HashMap<String, Resource> cqlParams) {

    for (CoverageRequirementRuleCriteria criteria : criteriaList) {
      logger.info("FhirBundleProcessor::buildExecutionContexts() criteria: " + criteria.toString());
      List<RuleMapping> rules = fileStore.findRules(criteria);

      for (RuleMapping rule: rules) {
        CoverageRequirementRuleResult result = new CoverageRequirementRuleResult();
        result.setCriteria(criteria).setTopic(rule.getTopic());
        try {
          logger.info("FhirBundleProcessor::buildExecutionContexts() found rule topic: " + rule.getTopic());

          //get the CqlRule
          CqlRule cqlRule = fileStore.getCqlRule(rule.getTopic(), rule.getFhirVersion());
          result.setContext(CqlExecutionContextBuilder.getExecutionContext(cqlRule, cqlParams, baseUrl));
          results.add(result);
        } catch (Exception e) {
          logger.info("r4/FhirBundleProcessor::buildExecutionContexts: failed processing cql bundle: " + e.getMessage());
        }
      }
    }
  }

  private String stripResourceType(String identifier) {
    int indexOfDivider = identifier.indexOf('/');
    if (indexOfDivider+1 == identifier.length()) {
      // remove the trailing '/'
      return identifier.substring(0, indexOfDivider);
    } else {
      return identifier.substring(indexOfDivider+1);
    }
  }

  private boolean idInSelectionsList(String identifier) {
    if (this.selections.isEmpty()) {
      // if selections list is empty, just assume we should process the request
      return true;
    } else {
      for ( String selection : selections) {
        if (identifier.contains(stripResourceType(selection))) {
          logger.info("r4/FhirBundleProcessor::idInSelectionsList(" + identifier + "): identifier found in selections list");
          return true;
        }
      }
      return false;
    }
  }

}

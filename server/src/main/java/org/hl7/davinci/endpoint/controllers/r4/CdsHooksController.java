package org.hl7.davinci.endpoint.controllers.r4;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.cdshooks.CdsResponse;
import org.hl7.davinci.endpoint.Utils;
import org.hl7.davinci.endpoint.cdshooks.services.crd.CdsServiceInformation;
import org.hl7.davinci.endpoint.cdshooks.services.crd.r4.OrderSelectService;
import org.hl7.davinci.endpoint.cdshooks.services.crd.r4.OrderSignService;
import org.hl7.davinci.r4.crdhook.CrdPrefetch;
import org.hl7.davinci.r4.crdhook.orderselect.OrderSelectRequest;
import org.hl7.davinci.r4.crdhook.ordersign.OrderSignRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController("r4_CdsHooksController")
public class CdsHooksController {

  static final Logger logger = LoggerFactory.getLogger(CdsHooksController.class);

  static final String FHIR_RELEASE = "/r4";
  static final String URL_BASE = "/cds-services";


  @Autowired private OrderSelectService orderSelectService;
  @Autowired private OrderSignService orderSignService;

  /**
   * The FHIR r4 services discovery endpoint.
   * @return A services object containing an array of all services available on this server
   */
  @CrossOrigin
  @GetMapping(value = FHIR_RELEASE + URL_BASE)
  public CdsServiceInformation serviceDiscovery() {
    logger.info("r4/serviceDiscovery");
    CdsServiceInformation serviceInformation = new CdsServiceInformation();
    serviceInformation.addServicesItem(orderSignService);
    serviceInformation.addServicesItem(orderSelectService);
    return serviceInformation;
  }

  /**
   * The coverage requirement discovery endpoint for the order select hook.
   * @param request An order select triggered cds request
   * @return The card response
   */
  @CrossOrigin
  @PostMapping(value = FHIR_RELEASE + URL_BASE + "/" + OrderSelectService.ID,
      consumes = "application/json;charset=UTF-8")
  public CdsResponse handleOrderSelect(@Valid @RequestBody OrderSelectRequest request, final HttpServletRequest httpServletRequest) {
    logger.info("r4/handleOrderSelect");
    if (request.getPrefetch() == null) {
      request.setPrefetch(new CrdPrefetch());
    }
    return orderSelectService.handleRequest(request, Utils.getApplicationBaseUrl(httpServletRequest));
  }

  /**
   * The coverage requirement discovery endpoint for the order sign hook.
   * @param request An order sign triggered cds request
   * @return The card response
   */
  @CrossOrigin
  @PostMapping(value = FHIR_RELEASE + URL_BASE + "/" + OrderSignService.ID,
      consumes = "application/json;charset=UTF-8")
  public CdsResponse handleOrderSign(@Valid @RequestBody OrderSignRequest request, final HttpServletRequest httpServletRequest) {
    logger.info("r4/handleOrderSign");

    String baseUrl = Utils.getApplicationBaseUrl(httpServletRequest).toString() + "/";
    logger.info("zzzz: baseUrl: " + baseUrl);

    logger.info("zzzz: fhirServer: " + request.getFhirServer());
    logger.info("zzzz: pathInfo: " + httpServletRequest.getPathInfo());
    logger.info("zzzz: contextPath: " + httpServletRequest.getContextPath());
    logger.info("zzzz: rquestURI: " + httpServletRequest.getRequestURI());
    logger.info("zzzz: servletPath: " + httpServletRequest.getServletPath());
    logger.info("zzzz: queryString: " + httpServletRequest.getQueryString());
    logger.info("zzzz: localAddr: " + httpServletRequest.getLocalAddr());
    logger.info("zzzz: localName: " + httpServletRequest.getLocalName());
    logger.info("zzzz: localPort: " + httpServletRequest.getLocalPort());
    logger.info("zzzz: serverName: " + httpServletRequest.getServerName());
    logger.info("zzzz: serverPort: " + httpServletRequest.getServerPort());
    logger.info("zzzz: remoteAddr: " + httpServletRequest.getRemoteAddr());
    logger.info("zzzz: remoteHost: " + httpServletRequest.getRemoteHost());
    logger.info("zzzz: remotePort: " + httpServletRequest.getRemotePort());

    if (request.getPrefetch() == null) {
      request.setPrefetch(new CrdPrefetch());
    }
    return orderSignService.handleRequest(request, Utils.getApplicationBaseUrl(httpServletRequest));
  }
}

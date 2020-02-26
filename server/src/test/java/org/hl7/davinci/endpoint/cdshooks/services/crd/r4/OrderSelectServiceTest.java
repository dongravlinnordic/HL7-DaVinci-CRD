package org.hl7.davinci.endpoint.cdshooks.services.crd.r4;

import org.cdshooks.CdsResponse;
import org.hl7.davinci.r4.CrdRequestCreator;
import org.hl7.davinci.r4.crdhook.orderselect.OrderSelectRequest;
import org.hl7.davinci.endpoint.cdshooks.services.crd.r4.OrderSelectService;
import org.hl7.fhir.r4.model.Enumerations;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderSelectServiceTest {

  static final Logger logger = LoggerFactory.getLogger(OrderSelectServiceTest.class);

  @Autowired
  private OrderSelectService service;

  @Test
  public void testHandleRequest() {
    URL applicationBase;
    try {
      applicationBase = new URL("http","localhost","/");
    } catch (MalformedURLException e){
      throw new RuntimeException(e);
    }
    Calendar cal = Calendar.getInstance();
    cal.set(1970, Calendar.JULY, 4);
    OrderSelectRequest request = CrdRequestCreator
        .createOrderSelectRequest(Enumerations.AdministrativeGender.MALE, cal.getTime(), "MA", "MA");
    CdsResponse response = service.handleRequest(request, applicationBase);
    assertNotNull(response);
    assertEquals(1, response.getCards().size());
    String summary = response.getCards().get(0).getSummary();
    assertTrue(summary.endsWith("No auth needed"));
  }
}

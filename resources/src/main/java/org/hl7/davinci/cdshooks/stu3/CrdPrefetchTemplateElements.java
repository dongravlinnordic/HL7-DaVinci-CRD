package org.hl7.davinci.cdshooks.stu3;

/**
 * Class that contains the different prefetch template elements used in crd requests.
 */
public class CrdPrefetchTemplateElements {

  public static final PrefetchTemplateElement DEVICE_REQUEST_BUNDLE = new PrefetchTemplateElement(
      "deviceRequestBundle",
      "DeviceRequest?id={{context.orders.DeviceRequest.id}}"
          + "&_include=DeviceRequest:patient"
          + "&_include=DeviceRequest:performer"
          + "&_include=DeviceRequest:requester"
          + "&_include=DeviceRequest:device"
          + "&_include=DeviceRequest:on-behalf"
          + "&_include=DeviceRequest:insurance:Coverage");
  public static final PrefetchTemplateElement MEDICATION_REQUEST_BUNDLE = new PrefetchTemplateElement(
      "medicationRequestBundle",
      "MedicationRequest?id={{context.orders.MedicationRequest.id}}"
          + "&_include=MedicationRequest:patient"
          + "&_include=MedicationRequest:intended-dispenser"
          + "&_include=MedicationRequest:requester:Practitioner"
          + "&_include=MedicationRequest:medication"
          + "&_include=MedicationRequest:on-behalf"
          + "&_include=MedicationRequest:insurance:Coverage");
  public static final PrefetchTemplateElement NUTRITION_ORDER_BUNDLE = new PrefetchTemplateElement(
      "nutritionOrderBundle",
      "NutritionOrder?id={{context.orders.NutritionOrder.id}}"
          + "&_include=NutritionOrder:patient"
          + "&_include=NutritionOrder:provider"
          + "&_include=NutritionOrder:requester"
          + "&_include=NutritionOrder:encounter"
          + "&_include=Enconuter:location"
          + "&_include=NutritionOrder:insurance:Coverage");
  public static final PrefetchTemplateElement PROCEDURE_REQUEST_BUNDLE = new PrefetchTemplateElement(
      "procedureRequestBundle",
      "ProcedureRequest?id={{context.orders.ProcedureRequest.id}}"
          + "&_include=ProcedureRequest:patient"
          + "&_include=ProcedureRequest:performer"
          + "&_include=ProcedureRequest:requester"
          + "&_include=ProcedureRequest:on-behalf"
          + "&_include=ProcedureRequest:insurance:Coverage");
  public static final PrefetchTemplateElement REFERRAL_REQUEST_BUNDLE = new PrefetchTemplateElement(
      "referralRequest",
      "ReferralRequest?id={{context.orders.ReferralRequest.id}}"
          + "&_include=ReferralRequest:patient"
          + "&_include=ReferralRequest:recipient"
          + "&_include=ReferralRequest:requester"
          + "&_include=ReferralRequest:on-behalf"
          + "&_include=ReferralRequest:insurance:Coverage");
  public static final PrefetchTemplateElement SUPPLY_REQUEST_BUNDLE = new PrefetchTemplateElement(
      "supplyRequestBundle",
      "SupplyRequest?id={{context.orders.SupplyRequest.id}}"
          + "&_include=SupplyRequest:patient"
          + "&_include=SupplyRequest:supplier:Organization"
          + "&_include=SupplyRequest:requester:Practitioner"
          + "&_include=SupplyRequest:requester:Organization"
          + "&_include=SupplyRequest:insurance:Coverage");

  public static class PrefetchTemplateElement {

    private String key;
    private String query;

    PrefetchTemplateElement(String key, String query) {
      this.key = key;
      this.query = query;
    }

    public String getKey() {
      return key;
    }

    public String getQuery() {
      return query;
    }
  }
}
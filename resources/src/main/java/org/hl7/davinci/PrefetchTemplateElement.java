package org.hl7.davinci;

public class PrefetchTemplateElement {

  private String key;
  private String[] queries;
  private Class returnType;

  /**
   * Describes a prefetch element. Note that the key and query are exposed in the service
   * endpoint, whereas the returnType is for internal use only.
   * @param key The key that will be associated with the result of the query in the prefetch.
   * @param query The query to execute against the fhir server.
   * @param returnType The type to cast the result of a successful query to (e.g. a Bundle)
   */
  public PrefetchTemplateElement(String key, Class returnType, String...queries) {
    this.key = key;
    this.queries = queries;
    this.returnType = returnType;
  }

  public String getKey() {
    return key;
  }

  public String[] getQuery() {
    return queries;
  }

  public Class getReturnType() {
    return returnType;
  }

  @Override
  public String toString(){
    return "[" + key + ", " + queries + ", " + returnType + "]";
  }
}
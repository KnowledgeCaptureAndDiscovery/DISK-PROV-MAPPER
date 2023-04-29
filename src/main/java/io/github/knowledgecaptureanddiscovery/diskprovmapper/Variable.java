package io.github.knowledgecaptureanddiscovery.diskprovmapper;

public class Variable {
  String id;
  String type;
  Object value;
  String label;

  public Variable(String id, String type, String value, String label) {
    this.id = id;
    this.type = type;
    this.value = value;
    this.label = label;
  }
}
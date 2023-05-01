package io.github.knowledgecaptureanddiscovery.diskprovmapper.Generator;

import java.util.Map;
import com.hubspot.jinjava.Jinjava;

public class DataNarrativeGenerator {
  String narrative;

  public String getNarrative() {
    return narrative;
  }

  public void setNarrative(String narrative) {
    this.narrative = narrative;
  }

  Jinjava jinjava = new Jinjava();

  public DataNarrativeGenerator(Map<String, Object> context, String dataNarrativeTemplate) {
    narrative = jinjava.render(dataNarrativeTemplate, context);
  }
}

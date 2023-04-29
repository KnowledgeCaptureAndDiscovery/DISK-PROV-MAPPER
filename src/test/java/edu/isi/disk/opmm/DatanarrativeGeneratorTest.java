package edu.isi.disk.opmm;

import java.util.Map;

import javax.annotation.Tainted;

import org.junit.Test;

import com.google.common.collect.Maps;

import io.github.knowledgecaptureanddiscovery.diskprovmapper.DataNarrativeGenerator;

public class DatanarrativeGeneratorTest {
  @Test
  public void testDatanarrativeGenerator() {
    String template = "Hello {{name}}!";
    Map<String, Object> context = Maps.newHashMap();
    context.put("name", "Maximiliano");
    DataNarrativeGenerator datanarrativeGenerator = new DataNarrativeGenerator(context, template);
    String narrative = datanarrativeGenerator.getNarrative();
    assert narrative.equals("Hello Maximiliano!");
  }
}

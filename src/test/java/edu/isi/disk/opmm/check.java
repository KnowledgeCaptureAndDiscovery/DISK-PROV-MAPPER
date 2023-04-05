package edu.isi.disk.opmm;

import java.io.File;
import java.io.IOException;

import org.diskproject.shared.classes.loi.TriggeredLOI;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class check {

  @Test
  public void check() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    String filePath = "src/test/resources/Hypothesis-4CGdVLyttD07/tloi.json";
    TriggeredLOI tloi = objectMapper.readValue(new File(filePath), TriggeredLOI.class);
    Assert.assertEquals("TriggeredLOI-tvUdPs5yRiWf", tloi.getId());
  }
}

package edu.isi.disk.opmm;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.diskproject.shared.classes.hypothesis.Hypothesis;
import org.diskproject.shared.classes.loi.LineOfInquiry;
import org.diskproject.shared.classes.loi.TriggeredLOI;
import org.diskproject.shared.classes.question.Question;
import org.junit.Test;
import org.openprovenance.prov.model.Bundle;
import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.Entity;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import junit.framework.Assert;

public class MapperTest {

  Document document;
  ProvDocumentReader provDocumentReader;

  public MapperTest() throws IOException {
    Hypothesis hypothesis = UtilsTest.loadHypothesis("src/test/resources/Hypothesis-4CGdVLyttD07/hypothesis.json");
    List<Question> questions = UtilsTest.loadQuestions("src/test/resources/Hypothesis-4CGdVLyttD07/questions.json");
    TriggeredLOI tloi = UtilsTest.loadTriggeredLOI("src/test/resources/Hypothesis-4CGdVLyttD07/tloi.json");
    List<TriggeredLOI> tlois = UtilsTest.loadTriggeredLOIs("src/test/resources/Hypothesis-4CGdVLyttD07/tlois.json");
    LineOfInquiry loi = UtilsTest.loadLineOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/loi.json");
    // List<LineOfInquiry> lois =
    // Utils.loadLinesOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/lois.json");
    try {
      Mapper mapper = new Mapper(hypothesis, loi, tlois, questions);
      DocumentProv documentProv = mapper.doc;
      document = documentProv.document;
      documentProv.doConversions("examples/document");
      provDocumentReader = new ProvDocumentReader(document);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void findQuestionByLocalName() throws StreamReadException, DatabindException, IOException, ParseException {
    Bundle questionBundle = provDocumentReader.getBundle(Constants.QUESTION_BUNDLE_NAME);
    String questionLocalName = "EnigmaQuestion5";
    Entity question = provDocumentReader.getEntityByLocalName(questionBundle, questionLocalName);
    Assert.assertEquals(questionLocalName, question.getId().getLocalPart());
  }

  @Test
  public void findHypothesisByLocalName() {
    Bundle hypothesisBundle = provDocumentReader.getBundle(Constants.HYPOTHESIS_BUNDLE_NAME);
    String hypothesisLocalName = "Hypothesis-4CGdVLyttD07";
    Entity hypothesis = provDocumentReader.getEntityByLocalName(hypothesisBundle, hypothesisLocalName);
    Assert.assertEquals(hypothesisLocalName, hypothesis.getId().getLocalPart());
  }

  @Test
  public void findLineOfInquiryByLocalName() {
    Bundle loiBundle = provDocumentReader.getBundle(Constants.LOIS_BUNDLE_NAME);
    String loiLocalName = "LOI-R12qZl77tJJ9";
    Entity loi = provDocumentReader.getEntityByLocalName(loiBundle, loiLocalName);
    Assert.assertEquals(loiLocalName, loi.getId().getLocalPart());
  }

  @Test
  public void findTriggerLOIByLocalName() {
    Bundle tloiBundle = provDocumentReader.getBundle(Constants.TLOIS_BUNDLE_NAME);
    String tloiLocalName = "TriggeredLOI-tvUdPs5yRiWf";
    Entity tloi = provDocumentReader.getEntityByLocalName(tloiBundle, tloiLocalName);
    Assert.assertEquals(tloiLocalName, tloi.getId().getLocalPart());
  }

  @Test
  public void findTriggerLOIByType() {
    Bundle tloiBundle = provDocumentReader.getBundle(Constants.TLOIS_BUNDLE_NAME);
    String tloiLocalName = "TriggeredLOI-tvUdPs5yRiWf";
    String type = "http://disk-project.org/ontology/disk#TriggeredLineOfInquiry";
    Entity tloi = provDocumentReader.getEntityByType(tloiBundle, type);
    Assert.assertEquals(tloiLocalName, tloi.getId().getLocalPart());
  }

  @Test
  public void findDatasetByType() {
    Bundle datasetBundle = provDocumentReader.getBundle(Constants.LOIS_BUNDLE_NAME);
    String type = "http://www.w3.org/ns/dcat#Catalog";
    Entity dataset = provDocumentReader.getEntityByType(datasetBundle, type);
    Assert.assertEquals(Constants.DCAT_CATALOG_LOCALNAME, dataset.getId().getLocalPart());
  }

  @Test
  public void numberBundlesTest() {
    Assert.assertEquals(4, provDocumentReader.bundles.size());
  }

}
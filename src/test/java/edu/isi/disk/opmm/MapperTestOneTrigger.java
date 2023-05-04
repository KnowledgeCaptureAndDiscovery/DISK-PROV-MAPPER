package edu.isi.disk.opmm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import edu.isi.kcap.diskproject.shared.classes.hypothesis.Hypothesis;
import edu.isi.kcap.diskproject.shared.classes.loi.LineOfInquiry;
import edu.isi.kcap.diskproject.shared.classes.loi.TriggeredLOI;
import edu.isi.kcap.diskproject.shared.classes.question.Question;
import org.junit.Test;
import org.openprovenance.prov.model.Bundle;
import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.Entity;
import org.openprovenance.prov.model.QualifiedName;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import io.github.knowledgecaptureanddiscovery.diskprovmapper.Constants;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.Mapper;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.ProvDocumentReader;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.DocumentProv;
import junit.framework.Assert;

public class MapperTestOneTrigger {

  Document document;
  ProvDocumentReader provDocumentReader;
  DocumentProv documentProv;

  public MapperTestOneTrigger() throws IOException, ParseException, URISyntaxException {
    Hypothesis hypothesis = UtilsTest.loadHypothesis("src/test/resources/Hypothesis-4CGdVLyttD07/hypothesis.json");
    List<Question> questions = UtilsTest.loadQuestions("src/test/resources/Hypothesis-4CGdVLyttD07/questions.json");
    List<TriggeredLOI> tlois = UtilsTest.loadTriggeredLOIs("src/test/resources/Hypothesis-4CGdVLyttD07/tlois.json");
    LineOfInquiry loi = UtilsTest.loadLineOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/loi.json");
    // List<LineOfInquiry> lois =
    // Utils.loadLinesOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/lois.json");
    Mapper mapper = new Mapper(hypothesis, loi, tlois.get(0), questions);
    documentProv = mapper.doc;
    document = documentProv.document;
    documentProv.write("examples/simple/document");
    provDocumentReader = new ProvDocumentReader(document);
  }

  @Test
  public void convertFormatProvN() {
    OutputStream out = new ByteArrayOutputStream();
    String formatProvN = "provn";
    documentProv.write(out, formatProvN);
    String strings = out.toString();
    Assert.assertTrue(strings.contains("document"));
  }

  @Test
  public void convertFormatTrig() {
    OutputStream out = new ByteArrayOutputStream();
    String formatProvN = "trig";
    documentProv.write(out, formatProvN);
    String strings = out.toString();
    Assert.assertTrue(strings.contains("@prefix"));
  }

  @Test
  public void findQuestionByLocalName() throws StreamReadException, DatabindException, IOException, ParseException {
    Bundle questionBundle = provDocumentReader.getBundle(Constants.BUNDLE_FRAMING_NAME);
    String questionLocalName = "EnigmaQuestion5";
    Entity question = provDocumentReader.getEntityByLocalName(questionBundle, questionLocalName);
    Assert.assertEquals(questionLocalName, question.getId().getLocalPart());
  }

  @Test
  public void findHypothesisByLocalName() {
    Bundle hypothesisBundle = provDocumentReader.getBundle(Constants.BUNDLE_HYPOTHESIS_NAME);
    String hypothesisLocalName = "Hypothesis-4CGdVLyttD07";
    Entity hypothesis = provDocumentReader.getEntityByLocalName(hypothesisBundle, hypothesisLocalName);
    Assert.assertEquals(hypothesisLocalName, hypothesis.getId().getLocalPart());
  }

  @Test
  public void findLineOfInquiryByLocalName() {
    Bundle loiBundle = provDocumentReader.getBundle(Constants.BUNDLE_LOIS_NAME);
    String loiLocalName = "LOI-R12qZl77tJJ9";
    Entity loi = provDocumentReader.getEntityByLocalName(loiBundle, loiLocalName);
    Assert.assertEquals(loiLocalName, loi.getId().getLocalPart());
  }

  @Test
  public void findTriggerLOIByLocalName() {
    String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
    Bundle bundle = provDocumentReader.getBundle(bundleName);
    String tloiLocalName = "TriggeredLOI-tvUdPs5yRiWf";
    Entity tloi = provDocumentReader.getEntityByLocalName(bundle, tloiLocalName);
    Assert.assertEquals(tloiLocalName, tloi.getId().getLocalPart());
  }

  @Test
  public void findTriggerLOIByType() {
    String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
    Bundle bundle = provDocumentReader.getBundle(bundleName);
    String tloiLocalName = "TriggeredLOI-tvUdPs5yRiWf";
    QualifiedName type = documentProv.qn(Constants.TRIGGER_LINE_OF_INQUIRY_LOCALNAME,
        DocumentProv.DISK_ONTOLOGY_PREFIX);
    Entity tloi = provDocumentReader.getEntityByType(bundle, type);
    Assert.assertEquals(tloiLocalName, tloi.getId().getLocalPart());
  }

  @Test
  public void findCatalog() {
    Bundle bundle = provDocumentReader.getBundle(Constants.BUNDLE_DATA_NAME);
    QualifiedName type = documentProv.qn(Constants.DCAT_CATALOG_LOCALNAME, DocumentProv.DCAT_PREFIX);
    Entity catalog = provDocumentReader.getEntityByType(bundle, type);
    Assert.assertEquals(Constants.DCAT_CATALOG_LOCALNAME, catalog.getId().getLocalPart());
  }

  @Test
  public void findDcatDataset() {
    // TODO: #9 DISK should stores the dcat resource in the lois bundle
    String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
    Bundle datasetBundle = provDocumentReader.getBundle(bundleName);
    QualifiedName type = documentProv.qn(Constants.DCAT_DATASET_LOCALNAME, DocumentProv.DCAT_PREFIX);
    List<Entity> resources = provDocumentReader.getEntitiesByType(datasetBundle,
        type);
    Assert.assertEquals(1, resources.size());
  }

  @Test
  public void findWorkflowArtifacts() {
    String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
    Bundle bundle = provDocumentReader.getBundle(bundleName);
    QualifiedName type = documentProv.qn(Constants.OPMW_WORKFLOW_EXECUTION_ARTIFACT_LOCALNAME,
        DocumentProv.OPMW_PREFIX);
    List<Entity> outputs = provDocumentReader.getEntitiesByType(bundle, type);
    Assert.assertEquals(4, outputs.size());
  }

  @Test
  public void findDataVariable() {
    String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
    Bundle datasetBundle = provDocumentReader.getBundle(bundleName);
    QualifiedName type = documentProv.qn(Constants.OPMW_WORKFLOW_EXECUTION_DATA_VARIABLE_LOCAL_NAME,
        DocumentProv.OPMW_PREFIX);
    List<Entity> resources = provDocumentReader.getEntitiesByType(datasetBundle,
        type);
    Assert.assertEquals(1, resources.size());
  }

  @Test
  public void findWorkflowParameterVariable() {
    String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
    Bundle datasetBundle = provDocumentReader.getBundle(bundleName);
    QualifiedName type = documentProv.qn(Constants.OPMW_WORKFLOW_EXECUTION_PARAMETER_VARIABLE_LOCAL_NAME,
        DocumentProv.OPMW_PREFIX);
    List<Entity> resources = provDocumentReader.getEntitiesByType(datasetBundle,
        type);
    Assert.assertEquals(5, resources.size());
  }

  @Test
  public void findDcatResource() {
    // TODO: #9 DISK should stores the dcat resource in the lois bundle
    String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
    Bundle datasetBundle = provDocumentReader.getBundle(bundleName);
    QualifiedName type = documentProv.qn(Constants.DCAT_RESOURCE_LOCALNAME, DocumentProv.DCAT_PREFIX);
    List<Entity> resources = provDocumentReader.getEntitiesByType(datasetBundle, type);
    Assert.assertEquals(10, resources.size());
  }

  @Test
  public void numberBundlesTest() {
    Assert.assertEquals(4, provDocumentReader.bundles.size());
  }

  @Test
  public void createTypeMapTest() {
    String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
    Bundle bundle = provDocumentReader.getBundle(bundleName);
    HashMap<String, List<Entity>> typeMap = provDocumentReader.createTypeMap(bundle);
    Assert.assertEquals(7, typeMap.size());
  }

}

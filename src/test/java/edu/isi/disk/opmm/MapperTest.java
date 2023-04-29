// package edu.isi.disk.opmm;

// import java.io.ByteArrayOutputStream;
// import java.io.IOException;
// import java.io.OutputStream;
// import java.net.URISyntaxException;
// import java.text.ParseException;
// import java.util.HashMap;
// import java.util.List;
// import edu.isi.kcap.diskproject.shared.classes.hypothesis.Hypothesis;
// import edu.isi.kcap.diskproject.shared.classes.loi.LineOfInquiry;
// import edu.isi.kcap.diskproject.shared.classes.loi.TriggeredLOI;
// import edu.isi.kcap.diskproject.shared.classes.question.Question;
// import org.junit.Test;
// import org.openprovenance.prov.model.Bundle;
// import org.openprovenance.prov.model.Document;
// import org.openprovenance.prov.model.Entity;
// import org.openprovenance.prov.vanilla.QualifiedName;

// import com.fasterxml.jackson.core.exc.StreamReadException;
// import com.fasterxml.jackson.databind.DatabindException;

// import io.github.knowledgecaptureanddiscovery.diskprovmapper.Constants;
// import io.github.knowledgecaptureanddiscovery.diskprovmapper.Mapper;
// import io.github.knowledgecaptureanddiscovery.diskprovmapper.DocumentProv;
// import
// io.github.knowledgecaptureanddiscovery.diskprovmapper.ProvDocumentReader;

// import junit.framework.Assert;

// public class MapperTest {

// Document documentMultiples;
// ProvDocumentReader provDocumentReaderMultiples;
// DocumentProv documentProvMultiples;
// Document documentSingle;
// ProvDocumentReader provDocumentReaderSingle;
// DocumentProv documentProvSingle;

// public MapperTest() throws IOException, ParseException, URISyntaxException {
// Hypothesis hypothesis =
// UtilsTest.loadHypothesis("src/test/resources/Hypothesis-4CGdVLyttD07/hypothesis.json");
// List<Question> questions =
// UtilsTest.loadQuestions("src/test/resources/Hypothesis-4CGdVLyttD07/questions.json");
// List<TriggeredLOI> tlois =
// UtilsTest.loadTriggeredLOIs("src/test/resources/Hypothesis-4CGdVLyttD07/tlois.json");
// LineOfInquiry loi =
// UtilsTest.loadLineOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/loi.json");
// // List<LineOfInquiry> lois =
// //
// Utils.loadLinesOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/lois.json");
// Mapper mapper = new Mapper(hypothesis, loi, tlois, questions);
// documentProvMultiples = mapper.doc;
// documentMultiples = documentProvMultiples.document;
// documentProvMultiples.doConversions("examples/documentMultiples");
// provDocumentReaderMultiples = new ProvDocumentReader(documentMultiples);

// Mapper mapperSingle = new Mapper(hypothesis, loi, tlois.get(0), questions);
// documentProvSingle = mapperSingle.doc;
// documentSingle = documentProvSingle.document;
// documentProvSingle.doConversions("examples/documentSimple");
// provDocumentReaderSingle = new ProvDocumentReader(documentSingle);

// }

// @Test
// public void convertFormatProvN() {
// OutputStream out = new ByteArrayOutputStream();
// String formatProvN = "provn";
// documentProvMultiples.convert(out, formatProvN);
// String strings = out.toString();
// Assert.assertTrue(strings.contains("document"));
// }

// @Test
// public void convertFormatTrig() {
// OutputStream out = new ByteArrayOutputStream();
// String formatProvN = "trig";
// documentProvMultiples.convert(out, formatProvN);
// String strings = out.toString();
// Assert.assertTrue(strings.contains("@prefix"));
// }

// @Test
// public void findQuestionByLocalName() throws StreamReadException,
// DatabindException, IOException, ParseException {
// Bundle questionBundle =
// provDocumentReaderMultiples.getBundle(Constants.QUESTION_BUNDLE_NAME);
// String questionLocalName = "EnigmaQuestion5";
// Entity question =
// provDocumentReaderMultiples.getEntityByLocalName(questionBundle,
// questionLocalName);
// Assert.assertEquals(questionLocalName, question.getId().getLocalPart());
// }

// @Test
// public void findHypothesisByLocalName() {
// Bundle hypothesisBundle =
// provDocumentReaderMultiples.getBundle(Constants.HYPOTHESIS_BUNDLE_NAME);
// String hypothesisLocalName = "Hypothesis-4CGdVLyttD07";
// Entity hypothesis =
// provDocumentReaderMultiples.getEntityByLocalName(hypothesisBundle,
// hypothesisLocalName);
// Assert.assertEquals(hypothesisLocalName, hypothesis.getId().getLocalPart());
// }

// @Test
// public void findLineOfInquiryByLocalName() {
// Bundle loiBundle =
// provDocumentReaderMultiples.getBundle(Constants.LOIS_BUNDLE_NAME);
// String loiLocalName = "LOI-R12qZl77tJJ9";
// Entity loi = provDocumentReaderMultiples.getEntityByLocalName(loiBundle,
// loiLocalName);
// Assert.assertEquals(loiLocalName, loi.getId().getLocalPart());
// }

// @Test
// public void findTriggerLOIByLocalName() {
// String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
// Bundle bundle = provDocumentReaderMultiples.getBundle(bundleName);
// String tloiLocalName = "TriggeredLOI-tvUdPs5yRiWf";
// Entity tloi = provDocumentReaderMultiples.getEntityByLocalName(bundle,
// tloiLocalName);
// Assert.assertEquals(tloiLocalName, tloi.getId().getLocalPart());
// }

// @Test
// public void findTriggerLOIByType() {
// String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
// Bundle bundle = provDocumentReaderMultiples.getBundle(bundleName);
// String tloiLocalName = "TriggeredLOI-tvUdPs5yRiWf";
// String type = "http://disk-project.org/ontology/disk#TriggeredLineOfInquiry";
// Entity tloi = provDocumentReaderMultiples.getEntityByType(bundle, type);
// Assert.assertEquals(tloiLocalName, tloi.getId().getLocalPart());
// }

// @Test
// public void findCatalog() {
// Bundle bundle =
// provDocumentReaderMultiples.getBundle(Constants.LOIS_BUNDLE_NAME);
// String type = "http://www.w3.org/ns/dcat#Catalog";
// Entity catalog = provDocumentReaderMultiples.getEntityByType(bundle, type);
// Assert.assertEquals(Constants.DCAT_CATALOG_LOCALNAME,
// catalog.getId().getLocalPart());
// }

// @Test
// public void findWorkflowArtifacts() {
// String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
// Bundle bundle = provDocumentReaderMultiples.getBundle(bundleName);
// String type = Constants.OPMW_WORKFLOW_EXECUTION_ARTIFACT_URL;
// List<Entity> outputs = provDocumentReaderMultiples.getEntitiesByType(bundle,
// type);
// Assert.assertEquals(4, outputs.size());
// }

// @Test
// public void findDcatDataset() {
// // TODO: #9 DISK should stores the dcat resource in the lois bundle
// String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
// Bundle datasetBundle = provDocumentReaderMultiples.getBundle(bundleName);
// String type = "http://www.w3.org/ns/dcat#Dataset";
// List<Entity> resources =
// provDocumentReaderMultiples.getEntitiesByType(datasetBundle,
// type);
// Assert.assertEquals(1, resources.size());
// }

// @Test
// public void findDataVariable() {
// String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
// Bundle datasetBundle = provDocumentReaderMultiples.getBundle(bundleName);
// String type = Constants.OPMW_WORKFLOW_EXECUTION_DATA_VARIABLE_URL;
// List<Entity> resources =
// provDocumentReaderMultiples.getEntitiesByType(datasetBundle,
// type);
// Assert.assertEquals(1, resources.size());
// }

// @Test
// public void findWorkflowParameterVariable() {
// String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
// Bundle datasetBundle = provDocumentReaderMultiples.getBundle(bundleName);
// String type = Constants.OPMW_WORKFLOW_EXECUTION_PARAMETER_VARIABLE_URL;
// List<Entity> resources =
// provDocumentReaderMultiples.getEntitiesByType(datasetBundle,
// type);
// Assert.assertEquals(5, resources.size());
// }

// @Test
// public void findDcatResource() {
// // TODO: #9 DISK should stores the dcat resource in the lois bundle
// String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
// Bundle datasetBundle = provDocumentReaderMultiples.getBundle(bundleName);
// QualifiedName type = new QualifiedName(DocumentProv.DCAT_NS, "Resource",
// DocumentProv.DCAT_PREFIX);
// List<Entity> resources =
// provDocumentReaderMultiples.getEntitiesByType(datasetBundle, type);
// Assert.assertEquals(10, resources.size());
// }

// @Test
// public void numberBundlesTest() {
// Assert.assertEquals(40, provDocumentReaderMultiples.bundles.size());
// }

// @Test
// public void createTypeMapTest() {
// String bundleName = "TriggeredLOI-tvUdPs5yRiWf";
// Bundle bundle = provDocumentReaderMultiples.getBundle(bundleName);
// HashMap<String, List<Entity>> typeMap =
// provDocumentReaderMultiples.createTypeMap(bundle);
// Assert.assertEquals(40, typeMap.size());
// }

// }

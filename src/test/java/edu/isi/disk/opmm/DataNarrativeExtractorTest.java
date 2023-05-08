package edu.isi.disk.opmm;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openprovenance.prov.model.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.isi.kcap.diskproject.shared.classes.hypothesis.Hypothesis;
import edu.isi.kcap.diskproject.shared.classes.loi.LineOfInquiry;
import edu.isi.kcap.diskproject.shared.classes.loi.TriggeredLOI;
import edu.isi.kcap.diskproject.shared.classes.question.Question;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.DocumentProv;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.Mapper;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.Extractor;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes.DataNarrativeVariableSchema;

public class DataNarrativeExtractorTest {
  Hypothesis hypothesis;
  List<Question> questions;
  List<TriggeredLOI> tlois;
  LineOfInquiry loi;

  public DataNarrativeExtractorTest() throws IOException, ParseException, URISyntaxException {
    hypothesis = UtilsTest.loadHypothesis("src/test/resources/Hypothesis-4CGdVLyttD07/hypothesis.json");
    questions = UtilsTest.loadQuestions("src/test/resources/Hypothesis-4CGdVLyttD07/questions.json");
    tlois = UtilsTest.loadTriggeredLOIs("src/test/resources/Hypothesis-4CGdVLyttD07/tlois.json");
    loi = UtilsTest.loadLineOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/loi.json");
  }

  @Test
  public void testConstructor() throws ParseException, URISyntaxException, IOException {
    Mapper mapper = new Mapper(this.hypothesis, this.loi, this.tlois.get(0), this.questions);
    DocumentProv documentProv = mapper.doc;
    Document document = documentProv.document;
    Extractor extractor = new Extractor(document);
    DataNarrativeVariableSchema narrativesVariables = extractor.getDataNarrativeVariable();
    extractor.getEntityGroups();
    System.out.println(extractor.getDataNarrativeVariable());
    ObjectMapper jsonMapper = new ObjectMapper();
    String json = jsonMapper.writeValueAsString(narrativesVariables);
    File file = new File("src/test/resources/Hypothesis-4CGdVLyttD07/narrativesVariables.json");
    FileUtils.writeStringToFile(file, json);
    System.out.println(json);
  }

  @Test
  public void testConstructorDirect() throws ParseException, URISyntaxException, IOException {
    Mapper mapper = new Mapper(this.hypothesis, this.loi, this.tlois.get(0), this.questions);
    DocumentProv documentProv = mapper.doc;
    Extractor extractor = new Extractor(documentProv);
    DataNarrativeVariableSchema narrativesVariables = extractor.getDataNarrativeVariable();
    extractor.getEntityGroups();
    System.out.println(extractor.getDataNarrativeVariable());
    ObjectMapper jsonMapper = new ObjectMapper();
    String json = jsonMapper.writeValueAsString(narrativesVariables);
    File file = new File("src/test/resources/Hypothesis-4CGdVLyttD07/narrativesVariables.json");
    FileUtils.writeStringToFile(file, json);
    System.out.println(json);
  }
}

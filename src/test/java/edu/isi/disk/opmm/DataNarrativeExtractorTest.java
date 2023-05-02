package edu.isi.disk.opmm;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Tainted;
import javax.xml.crypto.Data;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openprovenance.prov.model.Document;

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
  public DataNarrativeExtractorTest() throws IOException, ParseException, URISyntaxException {
    Hypothesis hypothesis = UtilsTest.loadHypothesis("src/test/resources/Hypothesis-4CGdVLyttD07/hypothesis.json");
    List<Question> questions = UtilsTest.loadQuestions("src/test/resources/Hypothesis-4CGdVLyttD07/questions.json");
    List<TriggeredLOI> tlois = UtilsTest.loadTriggeredLOIs("src/test/resources/Hypothesis-4CGdVLyttD07/tlois.json");
    LineOfInquiry loi = UtilsTest.loadLineOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/loi.json");
    Mapper mapper = new Mapper(hypothesis, loi, tlois.get(0), questions);
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
  public void testConstructor() {
  }
}

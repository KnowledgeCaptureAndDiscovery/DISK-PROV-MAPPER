package edu.isi.disk.opmm;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Tainted;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openprovenance.prov.model.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import edu.isi.kcap.diskproject.shared.classes.hypothesis.Hypothesis;
import edu.isi.kcap.diskproject.shared.classes.loi.LineOfInquiry;
import edu.isi.kcap.diskproject.shared.classes.loi.TriggeredLOI;
import edu.isi.kcap.diskproject.shared.classes.question.Question;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.DocumentProv;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.Mapper;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.Extractor;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes.DataNarrativeVariableSchema;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.Generator.DataNarrativeGenerator;

public class DatanarrativeGeneratorTest {

  Hypothesis hypothesis = UtilsTest.loadHypothesis("src/test/resources/Hypothesis-4CGdVLyttD07/hypothesis.json");
  List<Question> questions = UtilsTest.loadQuestions("src/test/resources/Hypothesis-4CGdVLyttD07/questions.json");
  List<TriggeredLOI> tlois = UtilsTest.loadTriggeredLOIs("src/test/resources/Hypothesis-4CGdVLyttD07/tlois.json");
  LineOfInquiry loi = UtilsTest.loadLineOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/loi.json");
  Mapper mapper = new Mapper(hypothesis, loi, tlois.get(0), questions);
  DocumentProv documentProv = mapper.doc;
  Document document = documentProv.document;
  Extractor extractor = new Extractor(document);
  DataNarrativeVariableSchema narrativesVariables = extractor.getDataNarrativeVariable();
  DataNarrativeGenerator datanarrativeGenerator;

  public DatanarrativeGeneratorTest() throws IOException, ParseException, URISyntaxException {
  }

  @Test
  public void testNarrativeFramingView() throws IOException {
    String template = FileUtils.readFileToString(new File("src/test/resources/templates/framing/basic.txt.jinja"),
        "UTF-8");
    datanarrativeGenerator = new DataNarrativeGenerator(narrativesVariables, template);
    Path path = new File("framingNarrative.md").toPath();
    datanarrativeGenerator.write(path);
  }

  @Test
  public void testNarrativeDataView() throws IOException {
    String template = FileUtils.readFileToString(new File("src/test/resources/templates/data/basic.txt.jinja"),
        "UTF-8");
    datanarrativeGenerator = new DataNarrativeGenerator(narrativesVariables, template);
    Path path = new File("dataNarrative.md").toPath();
    datanarrativeGenerator.write(path);
  }

  @Test
  public void testNarrativeDataViewFull() throws IOException {
    String template = FileUtils.readFileToString(new File("src/test/resources/templates/data/medium.jinja"),
        "UTF-8");
    datanarrativeGenerator = new DataNarrativeGenerator(narrativesVariables, template);
    String narrative = datanarrativeGenerator.getNarrative();
    Path path = new File("dataNarrativeFull.md").toPath();
    datanarrativeGenerator.write(path);
  }
}

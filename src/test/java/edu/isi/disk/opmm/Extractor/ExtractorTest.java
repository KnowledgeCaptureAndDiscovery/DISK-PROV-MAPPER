package edu.isi.disk.opmm.Extractor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.openprovenance.prov.model.Document;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import edu.isi.disk.opmm.UtilsTest;
import edu.isi.kcap.diskproject.shared.classes.hypothesis.Hypothesis;
import edu.isi.kcap.diskproject.shared.classes.loi.LineOfInquiry;
import edu.isi.kcap.diskproject.shared.classes.loi.TriggeredLOI;
import edu.isi.kcap.diskproject.shared.classes.question.Question;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.Constants;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.DocumentProv;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.Mapper;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.Extractor;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes.DataNarrativeVariableSchema;
import junit.framework.Assert;

public class ExtractorTest {
  Extractor extractor;
  private DataNarrativeVariableSchema dataNarrative;

  public ExtractorTest()
      throws StreamReadException, DatabindException, IOException, ParseException, URISyntaxException {

    Hypothesis hypothesis = UtilsTest.loadHypothesis("src/test/resources/Hypothesis-4CGdVLyttD07/hypothesis.json");
    List<Question> questions = UtilsTest.loadQuestions("src/test/resources/Hypothesis-4CGdVLyttD07/questions.json");
    List<TriggeredLOI> tlois = UtilsTest.loadTriggeredLOIs("src/test/resources/Hypothesis-4CGdVLyttD07/tlois.json");
    LineOfInquiry loi = UtilsTest.loadLineOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/loi.json");
    Mapper mapper = new Mapper(hypothesis, loi, tlois.get(0), questions);
    DocumentProv documentProv = mapper.doc;
    Document document = documentProv.document;
    extractor = new Extractor(document);
    dataNarrative = extractor.getDataNarrativeVariable();
  }

  @Test
  public void testDataQueryGroups() {
    List<String> variables = dataNarrative.getVariableGroupsName();
    Assert.assertTrue(variables.contains(Constants.DCAT_DATASET_LOCALNAME));
    Assert.assertTrue(variables.contains(Constants.DCAT_CATALOG_LOCALNAME));
    Assert.assertTrue(variables.contains(Constants.DCAT_QUERY_LOCALNAME));
    Assert.assertTrue(variables.contains(Constants.DCAT_RESOURCE_LOCALNAME));
  }

  @Test
  public void testFraming() {
    List<String> variables = dataNarrative.getVariableGroupsName();
    System.out.println(variables);
    Assert.assertTrue(variables.contains(Constants.SQO_QUESTION_LOCALNAME));
    Assert.assertTrue(variables.contains(Constants.SQO_QUESTION_VARIABLE_LOCALNAME));
    Assert.assertTrue(variables.contains(Constants.DISK_HYPOTHESIS_LOCALNAME));
  }
}

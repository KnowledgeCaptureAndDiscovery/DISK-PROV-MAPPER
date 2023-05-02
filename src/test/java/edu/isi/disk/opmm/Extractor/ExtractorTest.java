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
import io.github.knowledgecaptureanddiscovery.diskprovmapper.DocumentProv;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.Mapper;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.Extractor;

public class ExtractorTest {
  public ExtractorTest()
      throws StreamReadException, DatabindException, IOException, ParseException, URISyntaxException {

    Hypothesis hypothesis = UtilsTest.loadHypothesis("src/test/resources/Hypothesis-4CGdVLyttD07/hypothesis.json");
    List<Question> questions = UtilsTest.loadQuestions("src/test/resources/Hypothesis-4CGdVLyttD07/questions.json");
    List<TriggeredLOI> tlois = UtilsTest.loadTriggeredLOIs("src/test/resources/Hypothesis-4CGdVLyttD07/tlois.json");
    LineOfInquiry loi = UtilsTest.loadLineOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/loi.json");
    Mapper mapper = new Mapper(hypothesis, loi, tlois.get(0), questions);
    DocumentProv documentProv = mapper.doc;
    Document document = documentProv.document;
    Extractor extractor = new Extractor(document);
  }

  @Test
  public void testConstructor() {
  }

}

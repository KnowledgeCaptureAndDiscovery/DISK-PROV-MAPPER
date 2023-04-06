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

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

public class MapperTest {

  @Test
  public void mapper() throws StreamReadException, DatabindException, IOException, ParseException {

    Hypothesis hypothesis = UtilsTest.loadHypothesis("src/test/resources/Hypothesis-4CGdVLyttD07/hypothesis.json");
    List<Question> questions = UtilsTest.loadQuestions("src/test/resources/Hypothesis-4CGdVLyttD07/questions.json");
    TriggeredLOI tloi = UtilsTest.loadTriggeredLOI("src/test/resources/Hypothesis-4CGdVLyttD07/tloi.json");
    List<TriggeredLOI> tlois = UtilsTest.loadTriggeredLOIs("src/test/resources/Hypothesis-4CGdVLyttD07/tlois.json");
    LineOfInquiry loi = UtilsTest.loadLineOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/loi.json");
    // List<LineOfInquiry> lois =
    // Utils.loadLinesOfInquiry("src/test/resources/Hypothesis-4CGdVLyttD07/lois.json");
    Mapper mapper = new Mapper(hypothesis, loi, tlois, questions);

    DocumentProv documentProv = mapper.doc;
    // create directory if it doesn't exist
    String outputDirectory = "/Users/mosorio/repos/disk-project/DISK-OPMW-Mapper/examples/";
    File directory = new File(outputDirectory);
    if (!directory.exists()) {
      directory.mkdir();
    }
    String documentProvFilePath = directory.getAbsolutePath() + '/' + "document";
    documentProv.doConversions(documentProv.document, documentProvFilePath);

  }

}

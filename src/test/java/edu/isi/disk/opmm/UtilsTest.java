package edu.isi.disk.opmm;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.isi.kcap.diskproject.shared.classes.hypothesis.Hypothesis;
import edu.isi.kcap.diskproject.shared.classes.loi.LineOfInquiry;
import edu.isi.kcap.diskproject.shared.classes.loi.TriggeredLOI;
import edu.isi.kcap.diskproject.shared.classes.question.Question;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilsTest {

  public static ObjectMapper objectMapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  public static Hypothesis loadHypothesis(String filePath) throws StreamReadException, DatabindException, IOException {
    return objectMapper.readValue(new File(filePath), Hypothesis.class);
  }

  public static TriggeredLOI loadTriggeredLOI(String filePath)
      throws StreamReadException, DatabindException, IOException {
    return objectMapper.readValue(new File(filePath), TriggeredLOI.class);
  }

  public static Question loadQuestion(String filePath) throws StreamReadException, DatabindException, IOException {
    return objectMapper.readValue(new File(filePath), Question.class);
  }

  public static List<Question> loadQuestions(String filePath) throws IOException {
    return objectMapper.readValue(new File(filePath), new TypeReference<List<Question>>() {
    });
  }

  public static List<TriggeredLOI> loadTriggeredLOIs(String filePath) throws IOException {
    return objectMapper.readValue(new File(filePath), new TypeReference<List<TriggeredLOI>>() {
    });
  }

  public static List<Hypothesis> loadHypotheses(String filePath) throws IOException {
    return objectMapper.readValue(new File(filePath), new TypeReference<List<Hypothesis>>() {
    });
  }

  public static LineOfInquiry loadLineOfInquiry(String filePath)
      throws StreamReadException, DatabindException, IOException {
    return objectMapper.readValue(new File(filePath), LineOfInquiry.class);
  }

  public static List<LineOfInquiry> loadLinesOfInquiry(String filePath) throws IOException {
    return objectMapper.readValue(new File(filePath), new TypeReference<List<LineOfInquiry>>() {
    });
  }
}

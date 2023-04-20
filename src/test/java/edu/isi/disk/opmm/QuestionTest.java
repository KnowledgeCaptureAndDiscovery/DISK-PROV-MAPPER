package edu.isi.disk.opmm;

import java.io.IOException;
import java.util.List;

import edu.isi.kcap.diskproject.shared.classes.question.Question;
import org.junit.Assert;
import org.junit.Test;

public class QuestionTest {

  @Test
  public void loadQuestions() throws IOException {
    String path = "src/test/resources/Hypothesis-4CGdVLyttD07/questions.json";
    List<Question> questions = UtilsTest.loadQuestions(path);
    Question question = questions.get(0);
    String questionId = "https://w3id.org/sqo/resource/EnigmaQuestion4";
    Assert.assertEquals(questionId, question.getId());
  }
}

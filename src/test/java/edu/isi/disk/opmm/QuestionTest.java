package edu.isi.disk.opmm;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.diskproject.shared.classes.loi.TriggeredLOI;
import org.diskproject.shared.classes.question.Question;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QuestionTest {

  @Test
  public void loadQuestions() throws IOException {
    String path = "src/test/resources/Hypothesis-4CGdVLyttD07/questions.json";
    List<Question> questions = Utils.loadQuestions(path);
    Question question = questions.get(0);
    String questionId = "https://w3id.org/sqo/resource/EnigmaQuestion4";
    Assert.assertEquals(questionId, question.getId());
  }
}

package org.innercircle.surveyapiapplication.domain.question.fixture;

import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.question.domain.ShortAnswerQuestion;

public class QuestionFixture {

    public static Question createShortQuestion() {
        return new ShortAnswerQuestion(1L, "설문항목이름", "설문항목설명", false);
    }

    public static Question createShortQuestion(Long questionId) {
        return new ShortAnswerQuestion(questionId, "설문항목이름", "설문항목설명", false);
    }

}

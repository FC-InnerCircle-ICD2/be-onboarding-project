package org.innercircle.surveyapiapplication.domain.question.fixture;

import org.innercircle.surveyapiapplication.domain.question.domain.LongAnswerQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.MultiChoiceQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.ShortAnswerQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.SingleChoiceQuestion;

import java.util.List;

public class QuestionFixture {

    public static ShortAnswerQuestion createShortAnswerQuestion() {
        return new ShortAnswerQuestion(1L, "설문항목이름", "설문항목설명", false);
    }

    public static ShortAnswerQuestion createShortAnswerQuestion(Long questionId) {
        return new ShortAnswerQuestion(questionId, "설문항목이름", "설문항목설명", false);
    }

    public static LongAnswerQuestion createLongAnswerQuestion() {
        return new LongAnswerQuestion(1L, "설문항목이름", "설문항목설명", false);
    }

    public static LongAnswerQuestion createLongAnswerQuestion(Long questionId) {
        return new LongAnswerQuestion(questionId, "설문항목이름", "설문항목설명", false);
    }

    public static SingleChoiceQuestion createSingleChoiceQuestion() {
        return new SingleChoiceQuestion(
            1L,
            "설문항목이름",
            "설문항목설명",
            false,
            List.of("설문항목답변1", "설문항목답변2", "설문항목답변3", "설문항목답변4", "설문항목답변5"));
    }

    public static SingleChoiceQuestion createSingleChoiceQuestion(Long questionId) {
        return new SingleChoiceQuestion(
            questionId,
            "설문항목이름",
            "설문항목설명",
            false,
            List.of("설문항목답변1", "설문항목답변2", "설문항목답변3", "설문항목답변4", "설문항목답변5"));
    }

    public static MultiChoiceQuestion createMultiChoiceQuestion() {
        return new MultiChoiceQuestion(
            1L,
            "설문항목이름",
            "설문항목설명",
            false,
            List.of("설문항목답변1", "설문항목답변2", "설문항목답변3", "설문항목답변4", "설문항목답변5"));
    }

    public static MultiChoiceQuestion createMultiChoiceQuestion(Long questionId) {
        return new MultiChoiceQuestion(
            questionId,
            "설문항목이름",
            "설문항목설명",
            false,
            List.of("설문항목답변1", "설문항목답변2", "설문항목답변3", "설문항목답변4", "설문항목답변5"));
    }


}

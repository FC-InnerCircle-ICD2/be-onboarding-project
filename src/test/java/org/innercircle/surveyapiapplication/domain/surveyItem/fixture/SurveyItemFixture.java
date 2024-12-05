package org.innercircle.surveyapiapplication.domain.surveyItem.fixture;

import org.innercircle.surveyapiapplication.domain.surveyItem.domain.LongAnswerSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.MultiChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.ShortAnswerSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SingleChoiceSurveyItem;

import java.util.List;

public class SurveyItemFixture {

    public static ShortAnswerSurveyItem createShortAnswerQuestion() {
        return new ShortAnswerSurveyItem(1L, "설문항목이름", "설문항목설명", false);
    }

    public static ShortAnswerSurveyItem createShortAnswerQuestion(Long questionId) {
        return new ShortAnswerSurveyItem(questionId, "설문항목이름", "설문항목설명", false);
    }

    public static LongAnswerSurveyItem createLongAnswerQuestion() {
        return new LongAnswerSurveyItem(1L, "설문항목이름", "설문항목설명", false);
    }

    public static LongAnswerSurveyItem createLongAnswerQuestion(Long questionId) {
        return new LongAnswerSurveyItem(questionId, "설문항목이름", "설문항목설명", false);
    }

    public static SingleChoiceSurveyItem createSingleChoiceQuestion() {
        return new SingleChoiceSurveyItem(
            1L,
            "설문항목이름",
            "설문항목설명",
            false,
            List.of("설문항목답변1", "설문항목답변2", "설문항목답변3", "설문항목답변4", "설문항목답변5"));
    }

    public static SingleChoiceSurveyItem createSingleChoiceQuestion(Long questionId) {
        return new SingleChoiceSurveyItem(
            questionId,
            "설문항목이름",
            "설문항목설명",
            false,
            List.of("설문항목답변1", "설문항목답변2", "설문항목답변3", "설문항목답변4", "설문항목답변5"));
    }

    public static MultiChoiceSurveyItem createMultiChoiceQuestion() {
        return new MultiChoiceSurveyItem(
            1L,
            "설문항목이름",
            "설문항목설명",
            false,
            List.of("설문항목답변1", "설문항목답변2", "설문항목답변3", "설문항목답변4", "설문항목답변5"));
    }

    public static MultiChoiceSurveyItem createMultiChoiceQuestion(Long questionId) {
        return new MultiChoiceSurveyItem(
            questionId,
            "설문항목이름",
            "설문항목설명",
            false,
            List.of("설문항목답변1", "설문항목답변2", "설문항목답변3", "설문항목답변4", "설문항목답변5"));
    }


}

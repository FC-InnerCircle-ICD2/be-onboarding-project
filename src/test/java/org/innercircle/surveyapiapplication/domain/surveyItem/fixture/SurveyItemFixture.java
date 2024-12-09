package org.innercircle.surveyapiapplication.domain.surveyItem.fixture;

import org.innercircle.surveyapiapplication.domain.surveyItem.domain.ParagraphSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.MultiChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.TextSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SingleChoiceSurveyItem;

import java.util.List;

public class SurveyItemFixture {

    public static TextSurveyItem createTextSurveyItem() {
        return new TextSurveyItem(1L, "설문항목이름", "설문항목설명", false);
    }

    public static TextSurveyItem createTextSurveyItem(Long questionId) {
        return new TextSurveyItem(questionId, "설문항목이름", "설문항목설명", false);
    }

    public static ParagraphSurveyItem createParagraphSurveyItem() {
        return new ParagraphSurveyItem(1L, "설문항목이름", "설문항목설명", false);
    }

    public static ParagraphSurveyItem createParagraphSurveyItem(Long questionId) {
        return new ParagraphSurveyItem(questionId, "설문항목이름", "설문항목설명", false);
    }

    public static SingleChoiceSurveyItem createSingleChoiceSurveyItem() {
        return new SingleChoiceSurveyItem(
            1L,
            "설문항목이름",
            "설문항목설명",
            false,
            List.of("설문항목답변1", "설문항목답변2", "설문항목답변3", "설문항목답변4", "설문항목답변5"));
    }

    public static SingleChoiceSurveyItem createSingleChoiceSurveyItem(Long questionId) {
        return new SingleChoiceSurveyItem(
            questionId,
            "설문항목이름",
            "설문항목설명",
            false,
            List.of("설문항목답변1", "설문항목답변2", "설문항목답변3", "설문항목답변4", "설문항목답변5"));
    }

    public static MultiChoiceSurveyItem createMultiChoiceSurveyItem() {
        return new MultiChoiceSurveyItem(
            1L,
            "설문항목이름",
            "설문항목설명",
            false,
            List.of("설문항목답변1", "설문항목답변2", "설문항목답변3", "설문항목답변4", "설문항목답변5"));
    }

    public static MultiChoiceSurveyItem createMultiChoiceSurveyItem(Long questionId) {
        return new MultiChoiceSurveyItem(
            questionId,
            "설문항목이름",
            "설문항목설명",
            false,
            List.of("설문항목답변1", "설문항목답변2", "설문항목답변3", "설문항목답변4", "설문항목답변5"));
    }


}

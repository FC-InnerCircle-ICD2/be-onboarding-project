package com.survey.www.surveys.dto.command;

import com.querydsl.core.annotations.QueryProjection;
import com.survey.www.surveys.code.SurveyQuestionType;

public record SurveyDetailQuestionCommand(Long surveyQuestionId
                                , SurveyQuestionType surveyQuestionType
                                , String surveyQuestionNm
                                , String surveyQuestionDescription
                                , boolean isRequired
                                , boolean isDeletedSurveyQuestion
                                , Long surveyQuestionOptionId
                                , String surveyQuestionOptionContent
                                , boolean isDeletedSurveyQuestionOption
                                , Long surveyQuestionOptionParentId) {
    @QueryProjection
    public SurveyDetailQuestionCommand {}
}

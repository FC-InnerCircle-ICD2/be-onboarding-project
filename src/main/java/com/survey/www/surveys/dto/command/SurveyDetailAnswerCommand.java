package com.survey.www.surveys.dto.command;

import com.querydsl.core.annotations.QueryProjection;
import com.survey.www.surveys.code.SurveyQuestionType;

public record SurveyDetailAnswerCommand(Long surveyQuestionId
                                      , SurveyQuestionType surveyQuestionType
                                      , String questionNm
                                      , Long surveyQuestionOptionId
                                      , String optionContent
                                      , String answerContents
                                      , Long selectedCnt
                                      , Long totalCnt
) {
    @QueryProjection
    public SurveyDetailAnswerCommand {}
}

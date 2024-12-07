package com.survey.www.surveys.repository;

import com.survey.www.surveys.dto.command.SurveyDetailAnswerCommand;

import java.util.List;

public interface SurveyDslRepository {
    List<SurveyDetailAnswerCommand> searchBySurveyId(Long surveyId, String questionNm, String optionContent, String answerContent);
}

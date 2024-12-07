package com.survey.www.surveys.repository;

import com.survey.www.surveys.dto.command.SurveyDetailQuestionCommand;

import java.util.List;

public interface SurveyQuestionsDslRepository {
    List<SurveyDetailQuestionCommand> searchBySurveyId(Long id);
}

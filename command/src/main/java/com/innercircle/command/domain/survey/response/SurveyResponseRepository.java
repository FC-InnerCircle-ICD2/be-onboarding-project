package com.innercircle.command.domain.survey.response;

public interface SurveyResponseRepository {

	String generateId();

	SurveyResponse save(SurveyResponse response);
}

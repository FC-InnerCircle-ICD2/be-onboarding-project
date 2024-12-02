package com.innercircle.command.domain.survey;

public interface SurveyResponseRepository {

	String generateId();

	SurveyResponse save(SurveyResponse response);
}

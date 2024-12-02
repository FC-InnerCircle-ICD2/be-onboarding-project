package com.innercircle.command.domain.survey;

public interface SurveyRepository {

	String generateId();

	Survey save(Survey survey);
}

package com.innercircle.command.domain.survey.response;

import java.util.Optional;

public interface SurveyResponseRepository {

	String generateId();

	SurveyResponse save(SurveyResponse response);

	Optional<SurveyResponse> findById(String id);
}

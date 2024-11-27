package com.innercircle.command.domain.survey;

import java.util.Optional;

public interface SurveyRepository {

	String generateId();

	Survey save(Survey survey);

	Optional<Survey> findById(String id);
}

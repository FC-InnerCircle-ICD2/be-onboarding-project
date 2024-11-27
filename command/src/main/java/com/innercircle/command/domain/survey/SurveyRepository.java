package com.innercircle.command.domain.survey;

import java.util.Optional;
import java.util.UUID;

public interface SurveyRepository {

	SurveyId generateId();

	Survey save(Survey survey);

	Optional<Survey> findById(UUID id);
}

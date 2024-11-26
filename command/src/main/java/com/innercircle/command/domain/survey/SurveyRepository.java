package com.innercircle.command.domain.survey;

import java.util.Optional;
import java.util.UUID;

public interface SurveyRepository {

	Survey save(Survey survey);

	Optional<Survey> findById(UUID id);
}

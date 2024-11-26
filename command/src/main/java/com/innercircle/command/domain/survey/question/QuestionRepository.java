package com.innercircle.command.domain.survey.question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository {

	List<Question> saveAll(List<Question> questions);

	Optional<Question> findById(UUID id);
}

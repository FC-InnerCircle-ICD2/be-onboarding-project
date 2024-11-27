package com.innercircle.command.domain.survey.question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

	String generateId();

	List<Question> saveAll(List<Question> questions);

	Optional<Question> findById(String id);
}

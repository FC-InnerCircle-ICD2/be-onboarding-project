package com.innercircle.command.domain.survey.response;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository {

	String generateId();

	Answer save(Answer answer);

	Optional<Answer> findById(String id);

	List<Answer> saveAll(List<Answer> answers);
}

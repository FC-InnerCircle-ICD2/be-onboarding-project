package com.innercircle.command.domain.survey.response;

import java.util.List;

public interface AnswerRepository {

	String generateId();

	List<Answer> saveAll(List<Answer> answers);
}

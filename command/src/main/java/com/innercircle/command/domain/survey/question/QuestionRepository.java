package com.innercircle.command.domain.survey.question;

import java.util.List;

public interface QuestionRepository {

	String generateId();

	List<Question> saveAll(List<Question> questions);

	List<Question> findBySurveyId(String surveyId);

	void deleteAll(List<Question> questions);
}

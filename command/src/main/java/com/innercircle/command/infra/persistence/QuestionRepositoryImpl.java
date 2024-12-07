package com.innercircle.command.infra.persistence;

import com.innercircle.command.domain.survey.question.Question;
import com.innercircle.command.domain.survey.question.QuestionRepository;
import com.innercircle.command.infra.persistence.generator.IdGenerator;
import com.innercircle.command.infra.persistence.jparepository.QuestionJpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

	private final QuestionJpaRepository jpaRepository;

	public QuestionRepositoryImpl(QuestionJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public String generateId() {
		return IdGenerator.generate().toString();
	}

	@Override
	public List<Question> saveAll(List<Question> questions) {
		return this.jpaRepository.saveAll(questions);
	}

	@Override
	public List<Question> findBySurveyId(String surveyId) {
		return this.jpaRepository.findBySurveyId(surveyId);
	}

	@Override
	public void deleteAll(List<Question> questions) {
		this.jpaRepository.deleteAll(questions);
	}
}

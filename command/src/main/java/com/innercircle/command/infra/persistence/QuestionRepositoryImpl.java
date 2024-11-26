package com.innercircle.command.infra.persistence;

import com.innercircle.command.domain.survey.question.Question;
import com.innercircle.command.domain.survey.question.QuestionRepository;
import com.innercircle.command.infra.persistence.jparepository.QuestionJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

	private final QuestionJpaRepository jpaRepository;

	public QuestionRepositoryImpl(QuestionJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public List<Question> saveAll(List<Question> questions) {
		return jpaRepository.saveAll(questions);
	}

	@Override
	public Optional<Question> findById(UUID id) {
		return jpaRepository.findById(id);
	}
}

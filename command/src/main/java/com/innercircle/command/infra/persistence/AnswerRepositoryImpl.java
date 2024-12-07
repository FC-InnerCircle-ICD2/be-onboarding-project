package com.innercircle.command.infra.persistence;

import com.innercircle.command.domain.survey.response.Answer;
import com.innercircle.command.domain.survey.response.AnswerRepository;
import com.innercircle.command.infra.persistence.generator.IdGenerator;
import com.innercircle.command.infra.persistence.jparepository.AnswerJpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerRepositoryImpl implements AnswerRepository {

	private final AnswerJpaRepository jpaRepository;

	public AnswerRepositoryImpl(AnswerJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public String generateId() {
		return IdGenerator.generate().toString();
	}

	@Override
	public List<Answer> saveAll(List<Answer> answers) {
		return this.jpaRepository.saveAll(answers);
	}
}

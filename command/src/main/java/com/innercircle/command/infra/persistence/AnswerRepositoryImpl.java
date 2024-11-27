package com.innercircle.command.infra.persistence;

import com.innercircle.command.domain.survey.response.Answer;
import com.innercircle.command.domain.survey.response.AnswerRepository;
import com.innercircle.command.infra.persistence.generator.IdGenerator;
import com.innercircle.command.infra.persistence.jparepository.AnswerJpaRepository;
import java.util.List;
import java.util.Optional;
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
	public Answer save(Answer answer) {
		return this.jpaRepository.save(answer);
	}

	@Override
	public Optional<Answer> findById(String id) {
		return this.jpaRepository.findById(id);
	}

	@Override
	public List<Answer> saveAll(List<Answer> answers) {
		return this.jpaRepository.saveAll(answers);
	}
}

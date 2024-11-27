package com.innercircle.command.infra.persistence;

import com.innercircle.command.domain.survey.Survey;
import com.innercircle.command.domain.survey.SurveyRepository;
import com.innercircle.command.infra.persistence.generator.IdGenerator;
import com.innercircle.command.infra.persistence.jparepository.SurveyJpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class SurveyRepositoryImpl implements SurveyRepository {

	private final SurveyJpaRepository jpaRepository;

	public SurveyRepositoryImpl(SurveyJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public String generateId() {
		return IdGenerator.generate().toString();
	}

	@Override
	public Survey save(Survey survey) {
		return jpaRepository.save(survey);
	}

	@Override
	public Optional<Survey> findById(String id) {
		return jpaRepository.findById(id);
	}
}

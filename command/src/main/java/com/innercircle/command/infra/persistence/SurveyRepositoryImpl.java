package com.innercircle.command.infra.persistence;

import com.innercircle.command.domain.survey.Survey;
import com.innercircle.command.domain.survey.SurveyRepository;
import com.innercircle.command.infra.persistence.jparepository.SurveyJpaRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class SurveyRepositoryImpl implements SurveyRepository {

	private final SurveyJpaRepository jpaRepository;

	public SurveyRepositoryImpl(SurveyJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public Survey save(Survey survey) {
		return jpaRepository.save(survey);
	}

	@Override
	public Optional<Survey> findById(UUID id) {
		return jpaRepository.findById(id);
	}
}

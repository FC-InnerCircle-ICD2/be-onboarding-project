package com.innercircle.command.infra.persistence;

import com.innercircle.command.domain.survey.response.SurveyResponse;
import com.innercircle.command.domain.survey.response.SurveyResponseRepository;
import com.innercircle.command.infra.persistence.generator.IdGenerator;
import com.innercircle.command.infra.persistence.jparepository.SurveyResponseJpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class SurveyResponseRepositoryImpl implements SurveyResponseRepository {

	private final SurveyResponseJpaRepository jpaRepository;

	public SurveyResponseRepositoryImpl(SurveyResponseJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public String generateId() {
		return IdGenerator.generate().toString();
	}

	@Override
	public SurveyResponse save(SurveyResponse response) {
		return jpaRepository.save(response);
	}

	@Override
	public Optional<SurveyResponse> findById(String id) {
		return jpaRepository.findById(id);
	}
}

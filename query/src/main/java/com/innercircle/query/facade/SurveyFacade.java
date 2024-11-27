package com.innercircle.query.facade;

import com.innercircle.query.controller.dto.SurveyDto;
import com.innercircle.query.infra.persistence.jparepository.QuestionJpaRepository;
import com.innercircle.query.infra.persistence.jparepository.SurveyJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class SurveyFacade {

	private final SurveyJpaRepository surveyJpaRepository;
	private final QuestionJpaRepository questionJpaRepository;

	public SurveyFacade(SurveyJpaRepository surveyJpaRepository, QuestionJpaRepository questionJpaRepository) {
		this.surveyJpaRepository = surveyJpaRepository;
		this.questionJpaRepository = questionJpaRepository;
	}

	public SurveyDto getSurvey(String id) {
		var survey = surveyJpaRepository.findById(id).orElseThrow(SurveyNotFoundException::new);
		var questions = questionJpaRepository.findBySurveyId(id);

		return SurveyDto.from(survey, questions);
	}
}

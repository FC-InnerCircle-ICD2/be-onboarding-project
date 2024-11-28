package com.innercircle.query.facade;

import com.innercircle.query.controller.dto.SurveyDto;
import com.innercircle.query.controller.dto.SurveyDto.QuestionDto;
import com.innercircle.query.controller.dto.SurveyResponseDto;
import com.innercircle.query.controller.dto.SurveyResponseDto.AnswerDto;
import com.innercircle.query.infra.persistence.jparepository.AnswerJpaRepository;
import com.innercircle.query.infra.persistence.jparepository.QuestionJpaRepository;
import com.innercircle.query.infra.persistence.jparepository.SurveyJpaRepository;
import com.innercircle.query.infra.persistence.jparepository.SurveyResponseJpaRepository;
import com.innercircle.query.infra.persistence.model.survey.question.Question;
import com.innercircle.query.infra.persistence.model.survey.response.Answer;
import com.innercircle.query.infra.persistence.model.survey.response.SurveyResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SurveyFacade {

	private final AnswerJpaRepository answerJpaRepository;
	private final QuestionJpaRepository questionJpaRepository;
	private final SurveyJpaRepository surveyJpaRepository;
	private final SurveyResponseJpaRepository surveyResponseJpaRepository;

	public SurveyFacade(AnswerJpaRepository answerJpaRepository, QuestionJpaRepository questionJpaRepository, SurveyJpaRepository surveyJpaRepository,
			SurveyResponseJpaRepository surveyResponseJpaRepository) {
		this.answerJpaRepository = answerJpaRepository;
		this.questionJpaRepository = questionJpaRepository;
		this.surveyJpaRepository = surveyJpaRepository;
		this.surveyResponseJpaRepository = surveyResponseJpaRepository;
	}

	@Transactional(readOnly = true)
	public SurveyDto getSurvey(String surveyId) {
		var survey = surveyJpaRepository.findById(surveyId).orElseThrow(SurveyNotFoundException::new);
		var questions = questionJpaRepository.findBySurveyId(surveyId);
		var surveyResponseIds = surveyResponseJpaRepository.findAllBySurveyId(surveyId).stream().map(SurveyResponse::getId).toList();
		var answers = answerJpaRepository.findBySurveyResponseIdIn(surveyResponseIds);

		var questionDtos = getQuestionDtos(questions);
		var answerDtoMap = getAnswerDtoMap(answers);
		var surveyResponseDtos = getSurveyResponseDtos(answerDtoMap);

		return new SurveyDto(survey.getId(), survey.getName(), survey.getDescription(), questionDtos, surveyResponseDtos);
	}

	private List<QuestionDto> getQuestionDtos(List<Question> questions) {
		return questions.stream()
				.map(question -> new SurveyDto.QuestionDto(question.getId(), question.getName(), question.getDescription(), question.isRequired(),
						question.getType(), question.getOptions()))
				.toList();
	}

	private Map<String, List<AnswerDto>> getAnswerDtoMap(List<Answer> answers) {
		return answers.stream()
				.collect(Collectors.groupingBy(
						Answer::getSurveyResponseId,
						Collectors.mapping(
								answer -> new AnswerDto(answer.getId(), answer.getQuestionId(), answer.getQuestionType(), answer.isRequired(),
										answer.getSelectedOptions(), answer.getText()),
								Collectors.toList()
						))
				);
	}

	private List<SurveyResponseDto> getSurveyResponseDtos(Map<String, List<AnswerDto>> answerDtoMap) {
		return answerDtoMap.entrySet().stream()
				.map(entry -> new SurveyResponseDto(entry.getKey(), entry.getValue()))
				.toList();
	}
}

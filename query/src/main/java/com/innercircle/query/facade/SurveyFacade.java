package com.innercircle.query.facade;

import com.innercircle.common.domain.survey.question.QuestionSnapshot;
import com.innercircle.common.domain.survey.response.LongTextAnswerContent;
import com.innercircle.common.domain.survey.response.MultipleChoiceAnswerContent;
import com.innercircle.common.domain.survey.response.ShortTextAnswerContent;
import com.innercircle.common.domain.survey.response.SingleChoiceAnswerContent;
import com.innercircle.query.controller.dto.AnswerContentDto;
import com.innercircle.query.controller.dto.LongTextAnswerContentDto;
import com.innercircle.query.controller.dto.MultipleChoiceAnswerContentDto;
import com.innercircle.query.controller.dto.ShortTextAnswerContentDto;
import com.innercircle.query.controller.dto.SingleChoiceAnswerContentDto;
import com.innercircle.query.controller.dto.SurveyDto;
import com.innercircle.query.controller.dto.SurveyResponseDto;
import com.innercircle.query.controller.dto.SurveyResponseDto.AnswerDto;
import com.innercircle.query.infra.persistence.jparepository.AnswerJpaRepository;
import com.innercircle.query.infra.persistence.jparepository.SurveyJpaRepository;
import com.innercircle.query.infra.persistence.jparepository.SurveyResponseJpaRepository;
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
	private final SurveyJpaRepository surveyJpaRepository;
	private final SurveyResponseJpaRepository surveyResponseJpaRepository;

	public SurveyFacade(AnswerJpaRepository answerJpaRepository, SurveyJpaRepository surveyJpaRepository,
			SurveyResponseJpaRepository surveyResponseJpaRepository) {
		this.answerJpaRepository = answerJpaRepository;
		this.surveyJpaRepository = surveyJpaRepository;
		this.surveyResponseJpaRepository = surveyResponseJpaRepository;
	}

	@Transactional(readOnly = true)
	public SurveyDto getSurvey(String surveyId) {
		var survey = surveyJpaRepository.findById(surveyId).orElseThrow(SurveyNotFoundException::new);
		var surveyResponseIds = surveyResponseJpaRepository.findAllBySurveyId(surveyId).stream().map(SurveyResponse::getId).toList();
		var answers = answerJpaRepository.findBySurveyResponseIdIn(surveyResponseIds);

		var answerDtoMap = getAnswerDtoMap(answers);
		var surveyResponseDtos = getSurveyResponseDtos(answerDtoMap);

		return new SurveyDto(survey.getId(), survey.getName(), survey.getDescription(), surveyResponseDtos);
	}

	private Map<String, List<AnswerDto>> getAnswerDtoMap(List<Answer> answers) {
		return answers.stream()
				.collect(Collectors.groupingBy(
						Answer::getSurveyResponseId,
						Collectors.mapping(
								answer -> {
									var questionDto = getQuestionDto(answer.getQuestionSnapshot());
									var answerContentDto = getAnswerContentDto(answer);
									return new AnswerDto(answer.getId(), questionDto, answerContentDto);
								},
								Collectors.toList()
						))
				);
	}

	private AnswerDto.QuestionDto getQuestionDto(QuestionSnapshot questionSnapshot) {
		return new AnswerDto.QuestionDto(questionSnapshot.getId(), questionSnapshot.getName(),
				questionSnapshot.getDescription(), questionSnapshot.isRequired(), questionSnapshot.getType(),
				questionSnapshot.getOptions());
	}

	private AnswerContentDto getAnswerContentDto(Answer answer) {
		return switch (answer.getContent()) {
			case ShortTextAnswerContent content -> new ShortTextAnswerContentDto(content.getType(), content.getText());
			case LongTextAnswerContent content -> new LongTextAnswerContentDto(content.getType(), content.getText());
			case SingleChoiceAnswerContent content -> new SingleChoiceAnswerContentDto(content.getType(), content.getSelectedOption());
			case MultipleChoiceAnswerContent content -> new MultipleChoiceAnswerContentDto(content.getType(), content.getSelectedOptions());
			default -> throw new IllegalArgumentException("Unexpected value: " + answer.getContent());
		};
	}

	private List<SurveyResponseDto> getSurveyResponseDtos(Map<String, List<AnswerDto>> answerDtoMap) {
		return answerDtoMap.entrySet().stream()
				.map(entry -> new SurveyResponseDto(entry.getKey(), entry.getValue()))
				.toList();
	}
}

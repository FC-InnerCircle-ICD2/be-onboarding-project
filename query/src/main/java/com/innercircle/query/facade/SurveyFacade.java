package com.innercircle.query.facade;

import com.innercircle.common.domain.survey.question.QuestionSnapshot;
import com.innercircle.common.domain.survey.question.QuestionType;
import com.innercircle.query.controller.dto.AnswerContentDto;
import com.innercircle.query.controller.dto.LongTextAnswerContentDto;
import com.innercircle.query.controller.dto.MultipleChoiceAnswerContentDto;
import com.innercircle.query.controller.dto.QuestionDto;
import com.innercircle.query.controller.dto.ShortTextAnswerContentDto;
import com.innercircle.query.controller.dto.SingleChoiceAnswerContentDto;
import com.innercircle.query.controller.dto.SurveyDto;
import com.innercircle.query.controller.dto.SurveyResponsesDto;
import com.innercircle.query.controller.dto.SurveyResponseDto;
import com.innercircle.query.controller.dto.SurveyResponseDto.AnswerDto;
import com.innercircle.query.infra.persistence.dao.AnswerDao;
import com.innercircle.query.infra.persistence.jparepository.QuestionJpaRepository;
import com.innercircle.query.infra.persistence.jparepository.SurveyJpaRepository;
import com.innercircle.query.infra.persistence.jparepository.SurveyResponseJpaRepository;
import com.innercircle.query.infra.persistence.model.survey.response.Answer;
import com.innercircle.query.infra.persistence.model.survey.response.LongTextAnswerContent;
import com.innercircle.query.infra.persistence.model.survey.response.MultipleChoiceAnswerContent;
import com.innercircle.query.infra.persistence.model.survey.response.ShortTextAnswerContent;
import com.innercircle.query.infra.persistence.model.survey.response.SingleChoiceAnswerContent;
import com.innercircle.query.infra.persistence.model.survey.response.SurveyResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SurveyFacade {

	private final AnswerDao answerDao;
	private final QuestionJpaRepository questionJpaRepository;
	private final SurveyJpaRepository surveyJpaRepository;
	private final SurveyResponseJpaRepository surveyResponseJpaRepository;

	public SurveyFacade(AnswerDao answerDao, QuestionJpaRepository questionJpaRepository, SurveyJpaRepository surveyJpaRepository,
			SurveyResponseJpaRepository surveyResponseJpaRepository) {
		this.answerDao = answerDao;
		this.questionJpaRepository = questionJpaRepository;
		this.surveyJpaRepository = surveyJpaRepository;
		this.surveyResponseJpaRepository = surveyResponseJpaRepository;
	}

	@Transactional(readOnly = true)
	public SurveyDto getSurvey(String surveyId) {
		var survey = surveyJpaRepository.findById(surveyId).orElseThrow(SurveyNotFoundException::new);
		var questions = questionJpaRepository.findBySurveyId(survey.getId());
		var questionDtos = questions.stream()
				.map(question -> new QuestionDto(question.getId(), question.getName(), question.getDescription(), question.isRequired(),
						question.getType(), question.getOptions()))
				.toList();
		return new SurveyDto(survey.getId(), survey.getName(), survey.getDescription(), questionDtos);
	}

	@Transactional(readOnly = true)
	public SurveyResponsesDto getSurveyResponses(String surveyId) {
		var survey = surveyJpaRepository.findById(surveyId).orElseThrow(SurveyNotFoundException::new);
		var surveyResponseIds = surveyResponseJpaRepository.findAllBySurveyId(surveyId).stream().map(SurveyResponse::getId).toList();
		var answers = answerDao.findBySurveyResponseIdIn(surveyResponseIds);

		var answerDtoMap = getAnswerDtoMap(answers);
		var surveyResponseDtos = getSurveyResponseDtos(answerDtoMap);

		return new SurveyResponsesDto(survey.getId(), survey.getName(), survey.getDescription(), surveyResponseDtos);
	}

	@Transactional(readOnly = true)
	public List<AnswerDto> searchAnswers(String query) {
		var answers = answerDao.search(query);
		return answers.stream()
				.map(answer -> {
					var questionDto = getQuestionDto(answer.getQuestionSnapshot());
					var answerContentDto = getAnswerContentDto(answer);
					return new AnswerDto(answer.getId(), questionDto, answerContentDto);
				})
				.toList();
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

	private QuestionDto getQuestionDto(QuestionSnapshot questionSnapshot) {
		return new QuestionDto(questionSnapshot.getQuestionId(), questionSnapshot.getQuestionName(), questionSnapshot.getQuestionDescription(),
				questionSnapshot.isQuestionRequired(), questionSnapshot.getQuestionType(), questionSnapshot.getQuestionOptions());
	}

	private AnswerContentDto getAnswerContentDto(Answer answer) {
		return switch (answer.getContent()) {
			case ShortTextAnswerContent content -> new ShortTextAnswerContentDto(QuestionType.SHORT_TEXT, content.getText());
			case LongTextAnswerContent content -> new LongTextAnswerContentDto(QuestionType.LONG_TEXT, content.getText());
			case SingleChoiceAnswerContent content -> new SingleChoiceAnswerContentDto(QuestionType.SINGLE_CHOICE, content.getSelectedOption());
			case MultipleChoiceAnswerContent content -> new MultipleChoiceAnswerContentDto(QuestionType.MULTIPLE_CHOICE, content.getSelectedOptions());
			default -> throw new IllegalArgumentException("Unexpected value: " + answer.getContent());
		};
	}

	private List<SurveyResponseDto> getSurveyResponseDtos(Map<String, List<AnswerDto>> answerDtoMap) {
		return answerDtoMap.entrySet().stream()
				.map(entry -> new SurveyResponseDto(entry.getKey(), entry.getValue()))
				.toList();
	}
}

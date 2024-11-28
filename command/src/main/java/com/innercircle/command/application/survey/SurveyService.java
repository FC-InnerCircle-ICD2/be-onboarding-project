package com.innercircle.command.application.survey;

import com.innercircle.command.application.survey.question.LongTextQuestionInput;
import com.innercircle.command.application.survey.question.MultipleChoiceQuestionInput;
import com.innercircle.command.application.survey.question.QuestionInput;
import com.innercircle.command.application.survey.question.ShortTextQuestionInput;
import com.innercircle.command.application.survey.question.SingleChoiceQuestionInput;
import com.innercircle.command.application.survey.response.QuestionResponseInput;
import com.innercircle.command.domain.Identifier;
import com.innercircle.command.domain.survey.Survey;
import com.innercircle.command.domain.survey.SurveyRepository;
import com.innercircle.command.domain.survey.question.Question;
import com.innercircle.command.domain.survey.question.QuestionRepository;
import com.innercircle.command.domain.survey.question.QuestionType;
import com.innercircle.command.domain.survey.response.Answer;
import com.innercircle.command.domain.survey.response.AnswerRepository;
import com.innercircle.command.domain.survey.response.SurveyResponse;
import com.innercircle.command.domain.survey.response.SurveyResponseRepository;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SurveyService {

	private static final int MAX_QUESTIONS = 10;

	private final SurveyRepository surveyRepository;
	private final QuestionRepository questionRepository;
	private final SurveyResponseRepository surveyResponseRepository;
	private final AnswerRepository answerRepository;

	public SurveyService(SurveyRepository surveyRepository, QuestionRepository questionRepository, SurveyResponseRepository surveyResponseRepository,
			AnswerRepository answerRepository) {
		this.surveyRepository = surveyRepository;
		this.questionRepository = questionRepository;
		this.surveyResponseRepository = surveyResponseRepository;
		this.answerRepository = answerRepository;
	}

	@Transactional
	public Identifier create(String name, String description, List<QuestionInput> questionInputs) {
		if (StringUtils.isAnyBlank(name, description)) {
			throw new IllegalArgumentException("Survey name or description must not be empty");
		}
		if (CollectionUtils.isEmpty(questionInputs) || questionInputs.size() > MAX_QUESTIONS) {
			throw new IllegalArgumentException("Survey must have between 1 and %d questions".formatted(MAX_QUESTIONS));
		}

		var survey = new Survey(surveyRepository.generateId(), name, description);
		var questions = getQuestions(survey.getId(), questionInputs);

		questions.forEach(Question::validate);

		questionRepository.saveAll(questions);
		surveyRepository.save(survey);
		return new Identifier(survey.getId());
	}

	@Transactional
	public Identifier createResponse(String surveyId, List<QuestionResponseInput> responseInputs) {
		var surveyQuestions = questionRepository.findBySurveyId(surveyId);
		if (CollectionUtils.isEmpty(surveyQuestions)) {
			throw new SurveyNotFoundException();
		}
		var responseQuestions = getResponseQuestions(surveyId, responseInputs);
		if (CollectionUtils.isEqualCollection(surveyQuestions, responseQuestions)) {
			var surveyResponse = new SurveyResponse(surveyResponseRepository.generateId(), surveyId);
			var answers = responseInputs.stream().map(input -> {
						var inputQuestion = input.getQuestion();
						var inputQuestionType = inputQuestion.getType();
						var text = isTextQuestionAnswer(inputQuestionType) ? input.getText() : null;
						var selectedOption = isChoiceQuestionAnswer(inputQuestionType) ? input.getSelectedOptions() : List.<String>of();

						return new Answer(answerRepository.generateId(), surveyResponse.getId(), input.getQuestionId(), inputQuestionType,
								inputQuestion.isRequired(), selectedOption, text);
					})
					.toList();

			answers.forEach(Answer::validate);

			answerRepository.saveAll(answers);
			surveyResponseRepository.save(surveyResponse);

			return new Identifier(surveyResponse.getId());
		}
		throw new IllegalArgumentException("Response questions do not match survey questions");
	}

	private List<Question> getQuestions(String surveyId, List<QuestionInput> questionInputs) {
		return questionInputs.stream()
				.map(input -> {
					var questionId = questionRepository.generateId();
					if (input instanceof ShortTextQuestionInput shortTextQuestionInput) {
						return new Question(questionId, surveyId, shortTextQuestionInput.getName(), shortTextQuestionInput.getDescription(),
								shortTextQuestionInput.isRequired(), shortTextQuestionInput.getType(), List.of());
					} else if (input instanceof LongTextQuestionInput longTextQuestionInput) {
						return new Question(questionId, surveyId, longTextQuestionInput.getName(), longTextQuestionInput.getDescription(),
								longTextQuestionInput.isRequired(), longTextQuestionInput.getType(), List.of());
					} else if (input instanceof SingleChoiceQuestionInput singleChoiceQuestionInput) {
						return new Question(questionId, surveyId, singleChoiceQuestionInput.getName(), singleChoiceQuestionInput.getDescription(),
								singleChoiceQuestionInput.isRequired(), singleChoiceQuestionInput.getType(), singleChoiceQuestionInput.getOptionNames());
					} else if (input instanceof MultipleChoiceQuestionInput multipleChoiceQuestionInput) {
						return new Question(questionId, surveyId, multipleChoiceQuestionInput.getName(), multipleChoiceQuestionInput.getDescription(),
								multipleChoiceQuestionInput.isRequired(), multipleChoiceQuestionInput.getType(), multipleChoiceQuestionInput.getOptionNames());
					}
					return null;
				})
				.toList();
	}

	private List<Question> getResponseQuestions(String surveyId, List<QuestionResponseInput> responseInputs) {
		return responseInputs.stream()
				.map(input -> {
					if (input.getQuestion() instanceof ShortTextQuestionInput shortTextQuestionInput) {
						return new Question(input.getQuestionId(), surveyId, shortTextQuestionInput.getName(), shortTextQuestionInput.getDescription(),
								shortTextQuestionInput.isRequired(), shortTextQuestionInput.getType(), List.of());
					} else if (input.getQuestion() instanceof LongTextQuestionInput longTextQuestionInput) {
						return new Question(input.getQuestionId(), surveyId, longTextQuestionInput.getName(), longTextQuestionInput.getDescription(),
								longTextQuestionInput.isRequired(), longTextQuestionInput.getType(), List.of());
					} else if (input.getQuestion() instanceof SingleChoiceQuestionInput singleChoiceQuestionInput) {
						return new Question(input.getQuestionId(), surveyId, singleChoiceQuestionInput.getName(), singleChoiceQuestionInput.getDescription(),
								singleChoiceQuestionInput.isRequired(), singleChoiceQuestionInput.getType(), singleChoiceQuestionInput.getOptionNames());
					} else if (input.getQuestion() instanceof MultipleChoiceQuestionInput multipleChoiceQuestionInput) {
						return new Question(input.getQuestionId(), surveyId, multipleChoiceQuestionInput.getName(),
								multipleChoiceQuestionInput.getDescription(),
								multipleChoiceQuestionInput.isRequired(), multipleChoiceQuestionInput.getType(), multipleChoiceQuestionInput.getOptionNames());
					}
					return null;
				})
				.toList();
	}

	private boolean isTextQuestionAnswer(QuestionType type) {
		return type == QuestionType.SHORT_TEXT || type == QuestionType.LONG_TEXT;
	}

	private boolean isChoiceQuestionAnswer(QuestionType type) {
		return type == QuestionType.SINGLE_CHOICE || type == QuestionType.MULTIPLE_CHOICE;
	}
}

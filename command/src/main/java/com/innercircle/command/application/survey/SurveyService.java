package com.innercircle.command.application.survey;

import com.innercircle.command.application.survey.question.LongTextQuestionInput;
import com.innercircle.command.application.survey.question.MultipleChoiceQuestionInput;
import com.innercircle.command.application.survey.question.QuestionInput;
import com.innercircle.command.application.survey.question.ShortTextQuestionInput;
import com.innercircle.command.application.survey.question.SingleChoiceQuestionInput;
import com.innercircle.command.domain.Identifier;
import com.innercircle.command.domain.survey.Survey;
import com.innercircle.command.domain.survey.SurveyRepository;
import com.innercircle.command.domain.survey.question.Question;
import com.innercircle.command.domain.survey.question.QuestionRepository;
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

	public SurveyService(SurveyRepository surveyRepository, QuestionRepository questionRepository) {
		this.surveyRepository = surveyRepository;
		this.questionRepository = questionRepository;
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
}

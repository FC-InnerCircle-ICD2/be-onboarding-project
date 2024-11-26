package com.innercircle.command.application.survey;

import com.innercircle.command.application.survey.question.QuestionInput;
import com.innercircle.command.domain.survey.Survey;
import com.innercircle.command.domain.survey.SurveyRepository;
import com.innercircle.command.domain.survey.question.Question;
import com.innercircle.command.domain.survey.question.QuestionRepository;
import com.innercircle.command.infra.persistence.generator.IdGenerator;
import java.util.List;
import java.util.UUID;
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
	public UUID create(String name, String description, List<QuestionInput> questionInputs) {
		if (StringUtils.isAnyBlank(name, description)) {
			throw new IllegalArgumentException("Survey name or description must not be empty");
		}
		if (CollectionUtils.isEmpty(questionInputs) || questionInputs.size() > MAX_QUESTIONS) {
			throw new IllegalArgumentException("Survey must have between 1 and %d questions".formatted(MAX_QUESTIONS));
		}

		var survey = new Survey(IdGenerator.generate(), name, description);
		var questions = questionInputs.stream()
				.map(input -> input.convert(survey.getId()))
				.toList();
		questions.forEach(Question::validate);

		questionRepository.saveAll(questions);
		surveyRepository.save(survey);
		return survey.getId();
	}
}

package com.innercircle.query.controller.dto;

import com.innercircle.query.infra.persistence.model.survey.Survey;
import com.innercircle.query.infra.persistence.model.survey.question.Question;
import com.innercircle.query.infra.persistence.model.survey.question.QuestionType;
import java.util.List;

public record SurveyDto(String id, String title, String description, List<QuestionDto> questions) {

	public record QuestionDto(String id, String name, String description, boolean required, QuestionType type, List<String> options) {

	}

	public static SurveyDto from(Survey survey, List<Question> questions) {
		return new SurveyDto(
				survey.getId(),
				survey.getName(),
				survey.getDescription(),
				questions.stream()
						.map(question -> new QuestionDto(
								question.getId(),
								question.getName(),
								question.getDescription(),
								question.isRequired(),
								question.getType(),
								question.getOptions()
						))
						.toList()
		);
	}
}

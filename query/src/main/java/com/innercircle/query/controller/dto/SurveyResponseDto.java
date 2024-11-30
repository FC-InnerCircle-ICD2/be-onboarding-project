package com.innercircle.query.controller.dto;

import com.innercircle.common.domain.survey.question.QuestionType;
import java.util.List;

public record SurveyResponseDto(String id, List<AnswerDto> answers) {


	public record AnswerDto(String id, QuestionDto question, AnswerContentDto content) {

		public record QuestionDto(String id, String name, String description, boolean required, QuestionType type, List<String> options) {

		}
	}
}

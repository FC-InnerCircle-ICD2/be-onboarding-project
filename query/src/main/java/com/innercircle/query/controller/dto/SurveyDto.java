package com.innercircle.query.controller.dto;

import com.innercircle.common.domain.survey.question.QuestionType;
import java.util.List;

public record SurveyDto(String id, String title, String description, List<QuestionDto> questions, List<SurveyResponseDto> responses) {

	public record QuestionDto(String id, String name, String description, boolean required, QuestionType type, List<String> options) {

	}
}

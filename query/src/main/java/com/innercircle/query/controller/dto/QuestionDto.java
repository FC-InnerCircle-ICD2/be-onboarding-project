package com.innercircle.query.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innercircle.common.domain.survey.question.QuestionType;
import java.util.List;

public record QuestionDto(String id, String name, String description, boolean required, QuestionType type,
						  @JsonInclude(Include.NON_EMPTY) List<String> options) {

}

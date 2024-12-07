package com.innercircle.query.controller.dto;

import java.util.List;

public record SurveyResponseDto(String id, List<AnswerDto> answers) {

}

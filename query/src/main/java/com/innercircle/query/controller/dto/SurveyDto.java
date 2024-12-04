package com.innercircle.query.controller.dto;

import java.util.List;

public record SurveyDto(String id, String name, String description, List<QuestionDto> questions) {

}

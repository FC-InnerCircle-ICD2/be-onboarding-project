package com.innercircle.query.controller.dto;

import java.util.List;

public record SurveyResponsesDto(String id, String title, String description, List<SurveyResponseDto> responses) {

}

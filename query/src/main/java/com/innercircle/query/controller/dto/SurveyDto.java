package com.innercircle.query.controller.dto;

import java.util.List;

public record SurveyDto(String id, String title, String description, List<SurveyResponseDto> responses) {

}

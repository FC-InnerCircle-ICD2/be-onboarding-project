package com.onboarding.api.web.survey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateQuestionReqDto(
    String title,
    String description,
    String type,
    @JsonProperty("isRequired") boolean isRequired,
    Integer orderIndex
) {

}

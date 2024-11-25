package com.onboarding.api.web.survey.dto;

public record CreateQuestionReqDto(
    String title,
    String description,
    String type,
    boolean isRequired,
    Integer orderIndex
) {

}

package com.onboarding.survey.survey.dto;

public record QuestionDto(
    String title,
    String description,
    String type,
    boolean isRequired,
    Integer orderIndex
) {

}

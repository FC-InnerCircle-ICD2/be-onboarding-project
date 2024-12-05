package com.innercicle.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record ReplySurvey(
    Long id,
    Long surveyId,
    String name,
    String replierEmail,
    String description,
    List<ReplySurveyItem> items
) {

}

package com.innercicle.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record ReplySurvey(
    String id,
    String title,
    String description,
    List<ReplySurveyItem> items
) {

}

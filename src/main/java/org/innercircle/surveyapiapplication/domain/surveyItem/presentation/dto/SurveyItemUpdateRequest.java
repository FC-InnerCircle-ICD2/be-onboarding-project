package org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto;

import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;

import java.util.List;

public record SurveyItemUpdateRequest(
    String name,
    String description,
    SurveyItemType type,
    boolean required,
    List<String> options
) {}

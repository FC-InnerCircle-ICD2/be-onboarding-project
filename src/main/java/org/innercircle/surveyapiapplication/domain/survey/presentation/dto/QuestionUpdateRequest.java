package org.innercircle.surveyapiapplication.domain.survey.presentation.dto;

import org.innercircle.surveyapiapplication.domain.question.domain.type.QuestionType;

import java.util.List;

public record QuestionUpdateRequest(
    String name,
    String description,
    QuestionType type,
    boolean required,
    List<String> options
) {}

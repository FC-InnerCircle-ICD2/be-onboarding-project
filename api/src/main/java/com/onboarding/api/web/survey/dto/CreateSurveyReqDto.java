package com.onboarding.api.web.survey.dto;

import java.util.List;

public record CreateSurveyReqDto(
    String name,
    String description,
    List<CreateQuestionReqDto> questions
) {

}

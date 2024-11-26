package com.onboarding.api.web.survey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record CreateQuestionReqDto(
    String title,
    String description,
    String type,
    boolean isRequired,
    Integer orderIndex,
    List<String> choices
) {

}

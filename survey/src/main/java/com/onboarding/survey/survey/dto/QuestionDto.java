package com.onboarding.survey.survey.dto;

import com.onboarding.survey.survey.entity.Question;
import com.onboarding.survey.survey.entity.Survey;
import com.onboarding.survey.survey.enums.QuestionType;
import java.util.List;
import lombok.Builder;

@Builder
public record QuestionDto(
    String title,
    String description,
    String type,
    boolean isRequired,
    Integer orderIndex,
    List<String> choices
) {
  public Question of(Survey survey) {
    return Question.builder()
        .title(title)
        .description(description)
        .type(QuestionType.valueOf(type))
        .isRequired(isRequired)
        .orderIndex(orderIndex)
        .survey(survey)
        .choices(choices)
        .build();
  }
}
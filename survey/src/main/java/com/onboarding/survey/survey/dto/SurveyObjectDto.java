package com.onboarding.survey.survey.dto;

import com.onboarding.survey.survey.entity.Question;
import com.onboarding.survey.survey.entity.Survey;
import java.util.List;
import lombok.Builder;

@Builder
public record SurveyObjectDto(
    String surveyName,
    String surveyDescription,
    List<QuestionDto> questions
) {
  public Survey of(List<Question> questions) {
    return Survey.builder()
        .name(surveyName)
        .description(surveyDescription)
        .questions(questions)
        .build();
  }
}

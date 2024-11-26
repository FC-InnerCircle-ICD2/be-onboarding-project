package com.onboarding.api.web.survey.dto.create;

import com.onboarding.survey.survey.dto.QuestionObject;
import com.onboarding.survey.survey.dto.SurveyObject;
import java.util.List;

public record SurveyCreateReqDto(
    String name,
    String description,
    List<QuestionCreateReqDto> questions
) {
  public SurveyObject surveyObjectOf() {
    return SurveyObject.builder()
        .surveyName(name)
        .surveyDescription(description)
        .questions(questionOf())
        .build();
  }

  public List<QuestionObject> questionOf() {
    return questions.stream()
        .map(question -> QuestionObject.builder()
            .title(question.title())
            .type(question.type())
            .isRequired(question.isRequired())
            .description(question.description())
            .orderIndex(question.orderIndex())
            .choices(question.choices())
            .build()).toList();
  }
}

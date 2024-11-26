package com.onboarding.api.web.survey.dto.update;

import com.onboarding.survey.survey.dto.QuestionObject;
import com.onboarding.survey.survey.dto.SurveyObject;
import java.util.List;

public record SurveyUpdateReqDto(
    String name,
    String description,
    List<QuestionUpdateReqDto> questions
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
            .id(question.id())
            .title(question.title())
            .type(question.type())
            .isRequired(question.isRequired())
            .description(question.description())
            .operation(question.operation())
            .orderIndex(question.orderIndex())
            .choices(question.choices())
            .build()).toList();
  }
}

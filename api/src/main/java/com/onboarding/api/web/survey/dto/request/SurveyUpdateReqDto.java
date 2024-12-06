package com.onboarding.api.web.survey.dto.request;

import com.onboarding.survey.object.QuestionObject;
import com.onboarding.survey.object.SurveyObject;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SurveyUpdateReqDto{

  private String name;
  private String description;
  private List<QuestionUpdateReqDto> questions;

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
            .choices(question.choices())
            .build()).toList();
  }
}

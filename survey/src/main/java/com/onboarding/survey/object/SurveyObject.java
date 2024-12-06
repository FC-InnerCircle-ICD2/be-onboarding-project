package com.onboarding.survey.object;

import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SurveyObject {
  private String surveyName;
  private String surveyDescription;
  private List<QuestionObject> questions;

  public Survey of(List<Question> questions) {
    return Survey.builder()
        .name(surveyName)
        .description(surveyDescription)
        .questions(questions)
        .build();
  }
}

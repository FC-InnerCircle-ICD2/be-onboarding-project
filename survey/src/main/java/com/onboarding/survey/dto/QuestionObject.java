package com.onboarding.survey.dto;

import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.enums.Operation;
import com.onboarding.survey.enums.QuestionType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestionObject {
  private Long id;
  private String title;
  private String description;
  private QuestionType type;
  private Operation operation;
  private boolean isRequired;
  private List<String> choices;
  private boolean isDeleted;

  public QuestionObject(String description, String title, QuestionType questionType, boolean isRequired, List<String> choices, boolean isDeleted) {
    this.title = title;
    this.description = description;
    this.type = questionType;
    this.isDeleted = isDeleted;
    this.choices = choices;
    this.isRequired = isRequired;
  }

  public Question of(Survey survey) {
    return Question.builder()
        .title(title)
        .description(description)
        .type(type)
        .isRequired(isRequired)
        .survey(survey)
        .choices(choices)
        .isDeleted(isDeleted)
        .build();
  }
}

package com.onboarding.survey.object;

import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.enums.Operation;
import com.onboarding.survey.enums.QuestionType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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


  public Question of(Survey survey) {
    log.info("isRequired: {}", isRequired);
    log.info("isDeleted: {}", isDeleted);
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

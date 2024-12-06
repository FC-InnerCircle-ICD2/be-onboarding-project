package com.onboarding.api.web.survey.dto.request;

import com.onboarding.survey.object.QuestionObject;
import com.onboarding.survey.object.SurveyObject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public class SurveyCreateReqDto{

  private String name;
  private String description;
  private List<QuestionCreateReqDto> questions;

  public SurveyObject surveyObjectOf() {
    return SurveyObject.builder()
        .surveyName(name)
        .surveyDescription(description)
        .questions(questionOf())
        .build();
  }

  public List<QuestionObject> questionOf() {
    log.info("questions: {}", questions.size());
    return questions.stream()
        .map(question -> {
          log.info("isRequired: {}", question.isRequired());
          log.info("isDeleted: {}", question.isDeleted());
          return QuestionObject.builder()
              .title(question.getTitle())
              .type(question.getType())
              .isRequired(question.isRequired())
              .description(question.getDescription())
              .isDeleted(question.isDeleted())
              .choices(question.getChoices())
              .build();
        }).toList();
  }
}

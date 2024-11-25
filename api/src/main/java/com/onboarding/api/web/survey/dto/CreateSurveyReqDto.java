package com.onboarding.api.web.survey.dto;

import com.onboarding.survey.survey.dto.QuestionDto;
import com.onboarding.survey.survey.dto.SurveyObjectDto;
import java.util.List;

public record CreateSurveyReqDto(
    String name,
    String description,
    List<CreateQuestionReqDto> questions
) {
  public SurveyObjectDto surveyObjectOf() {
    return SurveyObjectDto.builder()
        .surveyName(name)
        .surveyDescription(description)
        .questions(questionOf())
        .build();
  }

  public List<QuestionDto> questionOf() {
    return questions.stream()
        .map(question -> QuestionDto.builder()
            .title(question.title())
            .type(question.type())
            .isRequired(question.isRequired())
            .description(question.description())
            .orderIndex(question.orderIndex())
            .build()).toList();
  }
}

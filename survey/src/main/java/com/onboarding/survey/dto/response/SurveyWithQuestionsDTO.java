package com.onboarding.survey.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SurveyWithQuestionsDTO {
  private Long id;
  private String name;
  private String description;
  private List<QuestionDTO> questions;
}

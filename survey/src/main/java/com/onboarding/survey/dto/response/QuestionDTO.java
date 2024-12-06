package com.onboarding.survey.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionDTO {
  private Long id;
  private String title;
  private String description;
  private String type;
  private boolean isRequired;
  private List<String> choices;
}

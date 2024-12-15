package com.fc.survey.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class SurveyResponse {

  private Long id;
  private String name;
  private List<AnswerResponse> answers;

  @Builder
  public SurveyResponse(Long id, String name, List<AnswerResponse> answers) {
    this.id = id;
    this.name = name;
    this.answers = answers;
  }

}

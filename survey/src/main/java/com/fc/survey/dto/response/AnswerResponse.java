package com.fc.survey.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class AnswerResponse {

  private final Long id;
  private final Long questionId;
  private final String questionName;
  private final String answer;

  @Builder
  public AnswerResponse(Long id, Long questionId, String questionName, String answer) {
    this.id = id;
    this.questionId = questionId;
    this.questionName = questionName;
    this.answer = answer;
  }

}

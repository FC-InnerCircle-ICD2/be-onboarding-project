package com.onboarding.api.web.response.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerRequest {
  private String questionTitle;
  private String questionType;
  private boolean isRequired;
  private String answer;
}

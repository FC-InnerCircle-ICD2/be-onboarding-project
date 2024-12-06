package com.onboarding.response.dto.response;

import com.onboarding.response.entity.ResponseValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnswerDTO {
  private String questionTitle;
  private ResponseValue responseValue;


}

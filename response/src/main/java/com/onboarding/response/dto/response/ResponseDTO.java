package com.onboarding.response.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDTO {
  private String email;
  private List<AnswerDTO> answers;
}

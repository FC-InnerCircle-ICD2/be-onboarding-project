package com.onboarding.api.web.response.dto.request;

import com.onboarding.response.object.AnswerObject;
import com.onboarding.response.object.ResponseObject;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubmitResponseRequest {
  private String email;
  private List<AnswerRequest> answers;

  public ResponseObject of() {
    // ResponseObject로 변환
    return ResponseObject.builder()
        .email(email)
        .answerObjects(
            answers.stream()
                .map(AnswerRequest::toAnswerObject) // AnswerObject로 변환
                .toList()
        )
        .build();
  }

}

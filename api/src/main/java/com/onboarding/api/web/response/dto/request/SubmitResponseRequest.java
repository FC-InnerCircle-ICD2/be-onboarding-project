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
    return ResponseObject.builder()
        .email(email)
        .answerObjects(answerObjects())
        .build();
  }

  private List<AnswerObject> answerObjects() {
    return answers.stream()
        .map(answerRequest -> AnswerObject.builder()
            .answer(answerRequest.getAnswer())
            .isRequired(answerRequest.isRequired())
            .questionType(answerRequest.getQuestionType())
            .questionTitle(answerRequest.getQuestionTitle())
            .build())
        .toList();
  }
}

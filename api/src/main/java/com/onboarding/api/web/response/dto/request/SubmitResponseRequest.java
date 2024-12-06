package com.onboarding.api.web.response.dto.request;

import com.onboarding.response.object.AnswerObject;
import com.onboarding.response.object.ResponseObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "응답 제출 요청")
public class SubmitResponseRequest {

  @Schema(description = "응답자 이메일", example = "test@gmail.com")
  private String email;

  @Schema(description = "질문 응답 리스트", example = """
         [
             {
               "questionTitle": "질문1",
               "questionType": "SHORT_ANSWER",
               "answer": "test",
               "choices": []
             }
         ]
    """)
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

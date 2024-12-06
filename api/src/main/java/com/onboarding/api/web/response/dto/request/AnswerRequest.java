package com.onboarding.api.web.response.dto.request;

import com.onboarding.response.object.AnswerObject;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerRequest {
  private String questionTitle; // 질문 제목
  private String questionType;  // 질문 타입
  private String answer;        // 응답 (텍스트)
  private List<String> choices; // 선택지 (옵션)

  public AnswerObject toAnswerObject() {
    return AnswerObject.builder()
        .questionTitle(questionTitle)
        .questionType(questionType)
        .answer(answer)  // 텍스트 응답
        .choices(choices != null ? choices : List.of()) // choices가 null인 경우 빈 리스트로 처리
        .build();
  }

}

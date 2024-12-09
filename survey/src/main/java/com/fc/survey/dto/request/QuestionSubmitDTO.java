package com.fc.survey.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "질문 응답 데이터 모델")
public class QuestionSubmitDTO {
  @Schema(description = "질문 식별자 값", example = "1")
  private Long id;
  @Schema(description = "질문 응답 내용", example = "choidasol")
  private String response;
}

package com.fc.survey.dto.request;


import com.fc.survey.entity.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "해당 설문조사의 개별 질문 데이터 모델")
public class QuestionRegDTO {

  @Schema(description = "질문명", example = "성함을 입력해주세요.")
  private String name;

  @Schema(description = "질문 상세 설명", example = "성함을 입력해주세요.")
  private String description;

  @Schema(description = "질문 유형, QuestionType Enum 값 ", example = "1")
  private QuestionType type;

  @Schema(description = "선택형 질문 유형 시 선택하는 값", example = "1")
  private String selectValue;

  @Schema(description = "답변 필수 여부", example = "false", defaultValue = "false")
  private boolean isRequired = false;

  @Schema(description = "해당 질문 수정 시 삭제 여부", example = "false", defaultValue = "false")
  private boolean isDelete = false;

}

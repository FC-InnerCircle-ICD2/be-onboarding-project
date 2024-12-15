package com.fc.survey.dto.request;

import com.fc.survey.entity.Question;
import com.fc.survey.entity.Survey;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
@Schema(description = "설문 등록 데이터 모델")
public class SurveyModDTO {

  @Schema(description = "설문조사 식별자 값", example = "1")
  private Long id;

  @Schema(description = "설문조사 명", example = "만족도 조사 설문")
  private String name;

  @Schema(description = "설문조사 상세 설명", example = "Inner Circle BE 2주차 OT 진행 만족도에 관한 설문조사 입니다.")
  private String description;

  @ArraySchema(
      schema = @Schema(implementation = QuestionModDTO.class))
  private List<QuestionModDTO> questions;

  public Survey toEntity() {
    Survey survey = Survey.builder()
        .id(id)
        .name(name)
        .description(description)
        .build();
    List<Question> questionEntities = questionDTOToEntity(survey);
    survey.setQuestions(questionEntities);
    return survey;
  }

  private List<Question> questionDTOToEntity(Survey survey) {
    return questions.stream().map(questionDTO -> Question.builder()
            .id(questionDTO.getId())
            .name(questionDTO.getName())
            .description(questionDTO.getDescription())
            .type(questionDTO.getType())
            .selectValue(questionDTO.getSelectValue())
            .isRequired(questionDTO.isRequired())
            .isDelete(questionDTO.isDelete())
            .survey(survey) // 부모 Survey 설정
            .build())
        .toList();
  }
}

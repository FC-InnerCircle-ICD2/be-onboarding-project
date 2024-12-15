package com.fc.survey.dto.request;

import com.fc.survey.entity.Answer;
import com.fc.survey.entity.AnswerQuestion;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "설문조사 제출 데이터 모델")
public class SurveySubmitDTO {

  @Schema(description = "설문조사 고유 식별 값", example = "1")
  private Long id;
  @Schema(description = "설문 응답자 이름", example = "최다솔")
  private String name;

  @ArraySchema(
      schema = @Schema(implementation = QuestionSubmitDTO.class))
  private List<QuestionSubmitDTO> questions;

  public Answer toEntity() {
    Answer answer = Answer.builder()
        .name(name)
        .build();
    List<AnswerQuestion> answerQuestions = answerQuestionDTOToEntity(answer);
    answer.setResponses(answerQuestions);
    return answer;
  }
  private List<AnswerQuestion> answerQuestionDTOToEntity(Answer answer) {
    return questions.stream().map(question -> AnswerQuestion.builder()
          .answer(answer)
          .response(question.getResponse())
          .build())
        .toList();
  }
}

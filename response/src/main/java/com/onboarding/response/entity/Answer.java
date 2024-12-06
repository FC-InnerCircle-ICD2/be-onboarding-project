package com.onboarding.response.entity;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.enums.QuestionType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Answer  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private QuestionSnapshot questionSnapshot; // 질문의 스냅샷

  @Embedded
  private ResponseValue responseValue; // 응답 값

  public Answer(QuestionSnapshot snapshot, ResponseValue response) {
    this.questionSnapshot = snapshot;
    this.responseValue = response;
  }

  public void validate() {
    if ((questionSnapshot.getType() == QuestionType.SINGLE_CHOICE || questionSnapshot.getType() == QuestionType.MULTIPLE_CHOICE) &&
        (responseValue.getChoiceResponses() == null || responseValue.getChoiceResponses().isEmpty())) {
      throw new CustomException("Choices must not be empty for SINGLE_CHOICE or MULTIPLE_CHOICE",
          ErrorCode.INVALID_INPUT_VALUE);
    }
    if ((questionSnapshot.getType() == QuestionType.SHORT_ANSWER || questionSnapshot.getType() == QuestionType.LONG_ANSWER) &&
        (responseValue.getTextResponse() == null || responseValue.getTextResponse().isEmpty())) {
      throw new CustomException("Text answer must not be empty for SHORT_ANSWER or LONG_ANSWER", ErrorCode.INVALID_INPUT_VALUE);
    }
  }


}

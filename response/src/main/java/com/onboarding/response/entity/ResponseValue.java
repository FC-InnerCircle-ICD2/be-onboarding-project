package com.onboarding.response.entity;

import com.onboarding.survey.enums.QuestionType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseValue {
  @Column(nullable = true)
  private String textResponse; // 텍스트 응답 (SHORT_ANSWER, LONG_TEXT용)

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "response_value_choices", joinColumns = @JoinColumn(name = "response_value_id"))
  @Column(name = "choice")
  private List<String> choiceResponses; // 선택 응답 (SINGLE_CHOICE, MULTIPLE_CHOICE용)

  public static ResponseValue forText(String textResponse) {
    return ResponseValue.builder().textResponse(textResponse).build();
  }

  public static ResponseValue forChoices(List<String> choiceResponses) {
    return ResponseValue.builder().choiceResponses(choiceResponses).build();
  }

  public boolean isValid(QuestionType type, boolean isRequired) {
    if (isRequired) {
      if ((type == QuestionType.SHORT_ANSWER || type == QuestionType.LONG_ANSWER) && (textResponse == null || textResponse.isBlank())) {
        return false;
      }
      if ((type == QuestionType.SINGLE_CHOICE || type == QuestionType.MULTIPLE_CHOICE) && (choiceResponses == null || choiceResponses.isEmpty())) {
        return false;
      }
    }
    return true;
  }

}

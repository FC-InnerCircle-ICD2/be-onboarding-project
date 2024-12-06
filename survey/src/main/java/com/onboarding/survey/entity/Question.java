package com.onboarding.survey.entity;

import com.onboarding.core.global.entity.BaseEntity;
import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.enums.QuestionType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Builder
@AllArgsConstructor
public class Question extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter
  private Long id;

  private String title;
  private String description;

  @Enumerated(EnumType.STRING)
  private QuestionType type;

  private boolean isRequired;
  private boolean isDeleted = false;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_id")
  private Survey survey;



  @ElementCollection
  @CollectionTable(name = "question_choices", joinColumns = @JoinColumn(name = "question_id"))
  @Column(name = "choice")
  @Builder.Default
  private List<String> choices = new ArrayList<>();

  public Question() {}

  public Question(String title, String description, QuestionType type, boolean isRequired, Survey survey, List<String> choices) {
    this.title = title;
    this.description = description;
    this.type = type;
    this.isRequired = isRequired;
    this.survey = survey;
    validateChoices(type, choices);
    this.choices = choices == null ? new ArrayList<>() : new ArrayList<>(choices);
  }

  private void validateChoices(QuestionType type, List<String> choices) {
    if (type == QuestionType.SINGLE_CHOICE || type == QuestionType.MULTIPLE_CHOICE) {
      if (choices == null || choices.isEmpty()) {
        throw new CustomException("Choices must be provided for single or multiple choice questions.", ErrorCode.MUST_BE_CHOICES);
      }
    } else if (type == QuestionType.SHORT_ANSWER || type == QuestionType.LONG_ANSWER) {
      if (choices != null && !choices.isEmpty()) {
        throw new CustomException("Choices are not allowed for short or long answer questions.", ErrorCode.INVALID_CHOICES);
      }
    }
  }

  public void updateDetails(String title, String description, QuestionType type, boolean isRequired, List<String> choices) {
    if (title != null && !title.isBlank()) {
      this.title = title;
    }
    if (description != null && !description.isBlank()) {
      this.description = description;
    }
    if (type != null) {
      this.type = type;
    }
    this.isRequired = isRequired;

    if (choices != null) {
      this.choices = new ArrayList<>(choices); // 깊은 복사로 데이터 안전성 보장
    } else {
      this.choices.clear(); // 비어 있는 경우 기존 데이터를 초기화
    }
  }


  public void setChoices(List<String> choices) {
    validateChoices(this.type, choices);
    this.choices = choices == null ? new ArrayList<>() : new ArrayList<>(choices);
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
  }
}

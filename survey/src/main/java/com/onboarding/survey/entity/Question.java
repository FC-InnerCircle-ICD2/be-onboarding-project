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
  private Long id;

  private String title;
  private String description;

  @Enumerated(EnumType.STRING)
  private QuestionType type;

  private boolean isRequired;
  private boolean isDeleted;

  @Setter
  private Integer orderIndex;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_id")
  private Survey survey;

  @ElementCollection
  @CollectionTable(name = "question_choices", joinColumns = @JoinColumn(name = "question_id"))
  @Column(name = "choice")
  @Builder.Default
  private List<String> choices = new ArrayList<>();

  public Question() {
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

    if (type == QuestionType.SINGLE_CHOICE || type == QuestionType.MULTIPLE_CHOICE) {
      if (choices == null || choices.isEmpty()) {
        throw new CustomException("Required question is missing: " ,
            ErrorCode.MUST_BE_CHOICES);
      }
      this.choices = new ArrayList<>(choices);
    }

    // orderIndex가 변경될 경우 처리
    if (orderIndex != null && !orderIndex.equals(this.orderIndex)) {
      int oldOrderIndex = this.orderIndex;
      int newOrderIndex = orderIndex;

      // 기존 질문의 orderIndex를 업데이트하고, 변경된 순서를 재조정
      survey.getQuestions().stream()
          .filter(q -> q.getOrderIndex() >= newOrderIndex && !q.getId().equals(this.id))
          .forEach(q -> q.setOrderIndex(q.getOrderIndex() + 1));

      this.orderIndex = newOrderIndex;

      // 기존 위치에 있던 질문들의 순서를 조정하여 중복 제거
      survey.getQuestions().stream()
          .filter(q -> q.getOrderIndex() > oldOrderIndex)
          .forEach(q -> q.setOrderIndex(q.getOrderIndex() - 1));
    }
  }



  public Question(Long id, String title, String description, QuestionType type, boolean isRequired,
      Integer orderIndex, Survey survey, List<String> choices) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.type = type;
    if (type == QuestionType.SINGLE_CHOICE || type == QuestionType.MULTIPLE_CHOICE) {
      if (choices == null || choices.isEmpty()) {
        throw new CustomException("Required question is missing: ",
            ErrorCode.MUST_BE_CHOICES);
      }
    }
    this.isRequired = isRequired;
    this.orderIndex = orderIndex;
    this.survey = survey;
    this.choices = choices;
  }

  public void setChoices(List<String> choices) {
    if (type == QuestionType.SINGLE_CHOICE || type == QuestionType.MULTIPLE_CHOICE) {
      if (choices == null || choices.isEmpty()) {
        throw new CustomException("Required question is missing: ",
            ErrorCode.MUST_BE_CHOICES);
      }
    }
    this.choices = choices;
  }
}

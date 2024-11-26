package com.onboarding.survey.survey.entity;

import com.onboarding.core.global.entity.BaseEntity;
import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.survey.enums.QuestionType;
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
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Builder
public class Question extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String description;

  @Enumerated(EnumType.STRING)
  private QuestionType type;

  private boolean isRequired;

  private Integer orderIndex;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_id")
  private Survey survey;

  @ElementCollection
  @CollectionTable(name = "question_choices", joinColumns = @JoinColumn(name = "question_id"))
  @Column(name = "choice")
  private List<String> choices = new ArrayList<>();

  public Question() {
  }

  public Question(Long id, String title, String description, QuestionType type, boolean isRequired,
      Integer orderIndex, Survey survey, List<String> choices) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.type = type;
    if (type == QuestionType.SINGLE_CHOICE || type == QuestionType.MULTIPLE_CHOICE) {
      if (choices == null || choices.isEmpty()) {
        throw new CustomException(ErrorCode.MUST_BE_CHOICES);
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
        throw new CustomException(ErrorCode.MUST_BE_CHOICES);
      }
    }
    this.choices = choices;
  }
}

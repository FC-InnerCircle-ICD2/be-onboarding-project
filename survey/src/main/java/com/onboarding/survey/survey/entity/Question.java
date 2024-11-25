package com.onboarding.survey.survey.entity;

import com.onboarding.core.global.entity.BaseEntity;
import com.onboarding.survey.survey.enums.QuestionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  public Question() {
  }

  public Question(Long id, String title, String description, QuestionType type, boolean isRequired,
      Integer orderIndex, Survey survey) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.type = type;
    this.isRequired = isRequired;
    this.orderIndex = orderIndex;
    this.survey = survey;
  }

}

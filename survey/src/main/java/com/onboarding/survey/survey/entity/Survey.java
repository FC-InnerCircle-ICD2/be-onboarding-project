package com.onboarding.survey.survey.entity;

import com.onboarding.core.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
public class Survey extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;

  @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Question> questions = new ArrayList<>();

  public Survey() {
  }

  public Survey(Long id, String name, String description, List<Question> questions) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.questions = questions;
  }

  public void addQuestion(Question question) {
    questions.add(question);
    question.setSurvey(this);
  }

  public void removeQuestion(Question question) {
    questions.remove(question);
    question.setSurvey(null);
  }
}

package com.onboarding.survey.entity;

import com.onboarding.core.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Builder
public class Survey extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;

  @Builder.Default
  @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("orderIndex ASC")
  private List<Question> questions = new ArrayList<>();

  public Survey() {
    this.questions = new ArrayList<>(); // 생성자에서 초기화
  }

  public Survey(Long id, String name, String description, List<Question> questions) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.questions = questions;
  }

  public void updateName(String name) {
    if (name != null && !name.isBlank()) {
      this.name = name;
    }
  }

  public void updateDescription(String description) {
    if (description != null && !description.isBlank()) {
      this.description = description;
    }
  }

  public void addQuestion(Question question) {
    if (questions == null) {
      questions = new ArrayList<>();
    }
    // 최대 질문 개수 제한
    if (questions.size() >= 10) {
      throw new IllegalArgumentException("Maximum of 10 questions allowed.");
    }

    // orderIndex가 지정되지 않은 경우 마지막 순서로 설정
    if (question.getOrderIndex() == null) {
      question.setOrderIndex(questions.size() + 1);
    }
    questions.add(question);
    question.setSurvey(this);

    // 정렬하여 순서 유지
    reorderQuestions();
  }



  // 질문 교환을 위해 재정렬
  public static void reorderQuestionsForNew(Question newQuestion, Question existingQuestion) {
    int newOrderIndex = newQuestion.getOrderIndex();
    int existingOrderIndex = existingQuestion.getOrderIndex();

    // 기존 질문의 순서를 새로운 질문의 다음 위치로 이동
    existingQuestion.setOrderIndex(existingOrderIndex + 1);

    // 새로운 질문의 orderIndex를 할당
    newQuestion.setOrderIndex(newOrderIndex);
  }

  private void reorderQuestions() {
    for (int i = 0; i < questions.size(); i++) {
      questions.get(i).setOrderIndex(i + 1);
    }
  }

  public void removeQuestion(Question question) {
    this.questions.remove(question);
    question.setSurvey(null); // 관계 해제
  }



}

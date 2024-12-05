package com.onboarding.response.entity;

import com.onboarding.survey.enums.QuestionType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Embeddable
public class QuestionSnapshot {

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private QuestionType type;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "question_snapshot_choices", joinColumns = @JoinColumn(name = "snapshot_id"))
  @Column(name = "choice")
  private List<String> choices = new ArrayList<>(); // 선택지

  @Column(nullable = false)
  private boolean isRequired;
  // 복사 생성자 추가
  public QuestionSnapshot(String title, String description, QuestionType type, List<String> choices, Boolean isRequired) {
    this.title = title;
    this.description = description;
    this.type = type;
    this.choices = (choices != null) ? List.copyOf(choices) : null; // 불변 컬렉션으로 복사
    this.isRequired = isRequired;
  }

  public QuestionSnapshot() {

  }
}

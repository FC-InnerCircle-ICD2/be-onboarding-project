package com.onboarding.response.entity;

import com.onboarding.survey.entity.Question;
import com.onboarding.survey.enums.QuestionType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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

  @Column(nullable = false)
  private String responseValue; // 응답 값

  @Embeddable
  @Builder
  public static record QuestionSnapshot(
      @Column(nullable = false)
      String title,

      @Column(nullable = false)
      String description,

      @Enumerated(EnumType.STRING)
      @Column(nullable = false)
      QuestionType type,

      @ElementCollection
      List<String> choices,

      @Column(nullable = false)
      Boolean isRequired

  ) {

  }
}

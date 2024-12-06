package com.onboarding.response.entity;

import com.onboarding.survey.entity.Survey;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_id", nullable = false)
  private Survey survey;

  @Column(nullable = false)
  private String email; // 응답자 식별자 (예: 사용자 ID)

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Answer> answers = new ArrayList<>();

  public <E> Response(String mail, List<E> desc) {
    this.email = mail;
    this.answers = new ArrayList<>();
  }

  public void addAnswer(Answer answer) {
    this.answers.add(answer);
  }
}

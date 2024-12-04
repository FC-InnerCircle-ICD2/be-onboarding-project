package com.fc.survey.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
public class Survey extends BaseTimeEntity{

  @Comment("설문조사 테이블 식별자")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("설문조사 이름")
  @Column
  private String name;

  @Comment("설문조사 설명")
  @Column
  private String description;

  @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
  private List<Question> questions;

  @Builder
  public Survey(String name, String description, List<Question> questions) {
    this.name = name;
    this.description = description;
    this.questions = questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }
}

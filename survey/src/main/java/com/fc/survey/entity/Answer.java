package com.fc.survey.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@NoArgsConstructor
@Data
public class Answer{

  @Comment("응답 테이블 식별자")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("설문자 명")
  @Column
  private String name;

  @ManyToOne
  @JoinColumn(name = "survey_id")
  private Survey survey;

  @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
  private List<AnswerQuestion> responses;

  @Builder
  public Answer(String name, Survey survey, List<AnswerQuestion> responses) {
    this.name = name;
    this.survey = survey;
    this.responses = responses;
  }

}

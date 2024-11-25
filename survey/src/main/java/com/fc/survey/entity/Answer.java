package com.fc.survey.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@NoArgsConstructor
public class Answer{

  @Comment("응답 테이블 식별자")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("설문자 명")
  @Column
  private String name;

  @OneToMany
  @JoinColumn(name="question_id")
  private List<Question> questions;

  @Comment("설문 응답 내용")
  @Column(columnDefinition = "TEXT")
  private String answer;

}

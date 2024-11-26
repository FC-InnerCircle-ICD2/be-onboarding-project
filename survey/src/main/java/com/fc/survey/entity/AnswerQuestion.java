package com.fc.survey.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Comment;

@Entity
public class AnswerQuestion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="answer_id")
  private Answer answer;

  @ManyToOne
  @JoinColumn(name="question_id")
  private Question question;

  @Comment("설문 응답 내용")
  @Column(columnDefinition = "TEXT")
  private String response;
}

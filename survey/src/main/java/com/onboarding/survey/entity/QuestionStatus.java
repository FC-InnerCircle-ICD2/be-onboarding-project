package com.onboarding.survey.entity;

import lombok.Getter;

@Getter
public class QuestionStatus {
  public enum Status {
    UNCHANGED, UPDATED, ADDED, DELETED
  }

  private final Status status;
  private final Question question;
  private final Question updatedQuestion;

  public QuestionStatus(Status status, Question question, Question updatedQuestion) {
    this.status = status;
    this.question = question;
    this.updatedQuestion = updatedQuestion;
  }

  public static QuestionStatus unchanged(Question question) {
    return new QuestionStatus(Status.UNCHANGED, question, null);
  }

  public static QuestionStatus updated(Question oldQuestion, Question newQuestion) {
    return new QuestionStatus(Status.UPDATED, oldQuestion, newQuestion);
  }

  public static QuestionStatus added(Question question) {
    return new QuestionStatus(Status.ADDED, null, question);
  }

  public static QuestionStatus deleted(Question question) {
    return new QuestionStatus(Status.DELETED, question, null);
  }
}

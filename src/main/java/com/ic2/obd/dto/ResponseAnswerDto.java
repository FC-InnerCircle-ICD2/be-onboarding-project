package com.ic2.obd.dto;

public class ResponseAnswerDto {
    private Long questionId; // 응답 대상 질문 ID
    private String answer;   // 응답 내용

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

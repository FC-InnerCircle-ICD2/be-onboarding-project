package com.onboarding.beonboardingproject.dto;

import com.onboarding.beonboardingproject.domain.response.Answer;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AnswerDto {
    private Long answerId;
    private String answerValue; // 응답 값
    private Long questionId;

    public AnswerDto(Long answerId, String answerValue, Long questionId) {
        this.answerId = answerId;
        this.answerValue = answerValue;
        this.questionId = questionId;
    }

    public static AnswerDto of(Answer answer) {
        return new AnswerDto(answer.getId(), answer.getAnswerValue(),answer.getQuestion().getId());
    }
}

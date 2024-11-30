package com.onboarding.beonboardingproject.dto;

import com.onboarding.beonboardingproject.domain.response.Answer;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AnswerDto {
    private Long questionId;
    private String answerValue; // 응답 값

    public AnswerDto(Long questionId, String answerValue) {
        this.questionId = questionId;
        this.answerValue = answerValue;
    }

    public static AnswerDto of(Answer answer) {
        return new AnswerDto(answer.getId(), answer.getAnswerValue());
    }
}

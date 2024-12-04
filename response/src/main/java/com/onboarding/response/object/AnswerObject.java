package com.onboarding.response.object;

import lombok.Builder;
import lombok.Getter;

@Builder
public record AnswerObject(
    String questionTitle,
    String questionType,
    boolean isRequired,
    String answer
) {

}

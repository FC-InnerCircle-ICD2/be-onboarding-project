package com.onboarding.response.object;

public record ResponseDetailObject(
    Long questionId,
    String questionType,
    boolean isRequired,
    String answer
) {

}

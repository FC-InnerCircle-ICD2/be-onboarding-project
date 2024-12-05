package com.onboarding.response.object;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
public record AnswerObject(
    String questionTitle,
    String questionType,
    boolean isRequired,
    String answer,
    List<String> choices
) {

}

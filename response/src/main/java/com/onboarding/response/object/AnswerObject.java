package com.onboarding.response.object;

import java.util.List;
import lombok.Builder;

@Builder
public record AnswerObject(
    String questionTitle,
    String questionType,
    boolean isRequired,
    String answer,
    List<String> choices// 선택지 (옵션)
) {

}

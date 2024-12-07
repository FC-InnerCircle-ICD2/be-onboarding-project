package com.innercicle.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record ReplySurveyItem(
    Long id,                // 식별자
    Long surveyItemId,
    String item,            // 항목
    String description,     // 설명
    InputType inputType,    // 입력 형태
    boolean required,       // 필수 여부
    String replyText,        // 응답 내용
    List<ItemOption> options    // 선택지 목록
) {

}

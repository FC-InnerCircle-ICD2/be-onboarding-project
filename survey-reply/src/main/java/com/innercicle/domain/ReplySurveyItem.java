package com.innercicle.domain;

import java.util.List;

public record ReplySurveyItem(
    Long id,                // 식별자
    String item,            // 항목
    String description,     // 설명
    InputType inputType,    // 입력 형태
    boolean required,       // 필수 여부
    String replyText,        // 응답 내용
    List<ItemOption> options    // 선택지 목록
) {

}

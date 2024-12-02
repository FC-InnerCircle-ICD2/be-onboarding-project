package com.innercicle.domain.v1;

import lombok.Builder;

import java.util.List;

/**
 * <h2>설문 항목</h2>
 *
 * @param id          식별자
 * @param item        항목
 * @param description 설명
 * @param inputType   입력 형태
 * @param required    필수 여부
 */
@Builder
public record SurveyItem(
    Long id,                // 식별자
    String item,            // 항목
    String description,     // 설명
    InputType inputType,    // 입력 형태
    boolean required,       // 필수 여부
    List<String> options    // 선택지 목록
) {

}

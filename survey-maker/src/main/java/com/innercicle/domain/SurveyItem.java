package com.innercicle.domain;

import java.util.List;

/**
 * <h2>설문 항목</h2>
 *
 * @param item        항목
 * @param description 설명
 * @param inputType   입력 형태
 * @param required    필수 여부
 */
public record SurveyItem(
    String item,            // 항목
    String description,     // 설명
    InputType inputType,    // 입력 형태
    boolean required,       // 필수 여부
    List<String> options    // 선택지 목록
) {

}

package com.innercicle.domain.v1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <h2>항목 입력 형태</h2>
 * <p>설문 항목의 입력 형태를 나타내는 열거형</p>
 */
@Getter
@RequiredArgsConstructor
public enum InputType {

    /**
     * 단답형
     */
    SHORT_TEXT("단답형"),
    /**
     * 장문형
     */
    LONG_TEXT("장문형"),
    /**
     * 단일 선택 목록형
     */
    SINGLE_SELECT("단일 선택 목록형"),
    /**
     * 다중 선택 목록형
     */
    MULTI_SELECT("다중 선택 목록형"),
    ;

    private final String type;

}

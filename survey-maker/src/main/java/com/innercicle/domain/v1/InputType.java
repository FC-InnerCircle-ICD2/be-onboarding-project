package com.innercicle.domain.v1;

import com.innercicle.advice.exceptions.RequiredFieldException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
    SHORT_TEXT("단답형") {
        @Override
        public void validateOptions(List<String> options) {
            if (options != null && !options.isEmpty()) {
                throw new RequiredFieldException(String.format("%s 타입은 옵션이 필요하지 않습니다.", getType()));
            }
        }
    },
    /**
     * 장문형
     */
    LONG_TEXT("장문형") {
        @Override
        public void validateOptions(List<String> options) {
            if (options != null && !options.isEmpty()) {
                throw new RequiredFieldException(String.format("%s 타입은 옵션이 필요하지 않습니다.", getType()));
            }
        }
    },
    /**
     * 단일 선택 목록형
     */
    SINGLE_SELECT("단일 선택 목록형") {
        @Override
        public void validateOptions(List<String> options) {
            if (options == null || options.size() < 2) {
                throw new RequiredFieldException(String.format("%s 타입은 2개 이상의 옵션이 필요합니다.", getType()));
            }
        }
    },
    /**
     * 다중 선택 목록형
     */
    MULTI_SELECT("다중 선택 목록형") {
        @Override
        public void validateOptions(List<String> options) {
            if (options == null || options.size() < 2) {
                throw new RequiredFieldException(String.format("%s 타입은 2개 이상의 옵션이 필요합니다.", getType()));
            }
        }
    };

    private final String type;

    public abstract void validateOptions(List<String> options);

}

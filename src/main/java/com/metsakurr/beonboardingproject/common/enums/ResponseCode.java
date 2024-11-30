package com.metsakurr.beonboardingproject.common.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("00000", "성공했습니다."),

    NOT_FOUND_REQUIRED_ANSWER("30000", "필수로 응답해야 하는 항목에 응답하지 않았습니다."),
    INVALID_SHORT_SENTENCE_ANSWER("30001", "단답형은 255자 미만으로 답할 수 있습니다."),
    INVALID_OPTION("30002", "선택 리스트에 없는 값을 선택했습니다."),
    INVALID_OPTION_IDX("30003", "유효하지 않은 옵션 인덱스 값입니다."),

    NOT_VALID_DATA("40000", "데이터 검증에 실패했습니다."),
    NOT_VALID_TYPE("40001", "파라미터의 타입을 확인해 주세요."),

    NOT_FOUND_SURVEY("40010", "유효하지 않은 설문조사입니다. [설문조사 이름], [설문조사 설명]을 확인해 주세요"),
    NOT_FOUND_ANSWER("40011", "응답해야 하는 문항에 대한 항목이 없습니다."),
    NOT_FOUND_SURVEY_IDX("40012", "idx[설문조사 식별자]가 필요합니다.")


    ;

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

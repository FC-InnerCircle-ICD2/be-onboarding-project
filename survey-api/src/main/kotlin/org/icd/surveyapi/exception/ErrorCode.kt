package org.icd.surveyapi.exception

enum class ErrorCode(val errorMessage: String) {
    MISSING_REQUIRED_FIELD("필수 파라미터가 입력되지 않았습니다."),
    DUPLICATE_SURVEY_ITEM_SEQUENCE("sequence 값이 중복되었습니다."),
    INVALID_SURVEY_ITEM_COUNT("항목은 1개에서 10개 사이만 입력 가능합니다."),

    NOT_FOUND_SURVEY("해당 설문조사를 찾을 수 없습니다."),

    INTERNAL_SERVER_ERROR("알 수 없는 오류가 발생했습니다."),
}
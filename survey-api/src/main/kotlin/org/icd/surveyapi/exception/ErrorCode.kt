package org.icd.surveyapi.exception

enum class ErrorCode(val errorMessage: String) {
    MISSING_REQUIRED_FIELD("필수 파라미터가 입력되지 않았습니다."),

    INTERNAL_SERVER_ERROR("알 수 없는 오류가 발생했습니다."),
}
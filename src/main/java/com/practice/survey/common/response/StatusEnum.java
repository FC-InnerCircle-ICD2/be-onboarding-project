package com.practice.survey.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusEnum {

    SUCCESS(200, "Success", HttpStatus.OK)
    , INVALID_REQUEST(400, "Invalid Request", HttpStatus.BAD_REQUEST)
    , INTERNAL_SERVER_ERROR(500, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR)
    , NOT_FOUND(404, "Not Found", HttpStatus.NOT_FOUND)

    // 설문조사 : 3000대
    , SURVEY_NOT_FOUND(3001, "설문조사를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
    , SURVEY_ALREADY_EXISTS(3002, "이미 존재하는 설문조사입니다.", HttpStatus.BAD_REQUEST)
    , SURVEY_ITEM_NOT_FOUND(3003, "설문조사 항목을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
    , SURVEY_ITEM_ALREADY_EXISTS(3004, "이미 존재하는 설문조사 항목입니다.", HttpStatus.BAD_REQUEST)
    , SURVEY_ITEM_OPTION_NOT_FOUND(3005, "설문조사 항목 옵션을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
    , SURVEY_ITEM_OPTION_ALREADY_EXISTS(3006, "이미 존재하는 설문조사 항목 옵션입니다.", HttpStatus.BAD_REQUEST)
    , SURVEY_VERSION_NOT_FOUND(3007, "설문조사 버전을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
    , SURVEY_VERSION_ALREADY_EXISTS(3008, "이미 존재하는 설문조사 버전입니다.", HttpStatus.BAD_REQUEST)
    , OPTION_REQUIRED(3009, "선택지를 입력해주세요.", HttpStatus.BAD_REQUEST)
    , OPTION_NOT_REQUIRED(3010, "선택지를 입력하지 않아도 됩니다.", HttpStatus.OK)

    // 응답 : 4000대
    , REQUIRED_FIELD_MISSING(4001, "필수 필드가 누락되었습니다.", HttpStatus.BAD_REQUEST)
    , INPUT_TYPE_MISMATCH(4002, "입력 유형이 일치하지 않습니다.", HttpStatus.BAD_REQUEST)
    , SHORT_TEXT_TOO_LONG(4003, "20자 미만으로 응답해주세요.", HttpStatus.BAD_REQUEST)
    , MULTIPLE_CHOICE_TOO_SHORT(4004, "2개 이상의 응답을 선택해주세요.", HttpStatus.BAD_REQUEST)
    , LONG_TEXT_LENGTH_MISMATCH(4005, "20자 이상 200자 이하로 응답해주세요.", HttpStatus.BAD_REQUEST)
    , SINGLE_CHOICE_MISMATCH(4006, "1개의 응답을 선택해주세요.", HttpStatus.BAD_REQUEST)
    , INVALID_OPTION(4007, "선택지 중 하나를 선택해주세요.", HttpStatus.BAD_REQUEST)

    // 공통 Exception 7000대
    , NOT_NULL_VIOLATE(7000, "널값이 들어갔습니다.")
    , DATA_LENGTH_VIOLATE(7001, "데이터 길이가 지켜지지 않았습니다.")
    , NOT_EMPTY_VIOLATE(7003, "빈값이 들어갔습니다.")
    , METHOD_NOT_SUPPORTED(7004, "지원하지 않는 메소드입니다.")
    ,UNSUPPORTED_MEDIA_TYPE(7005, "지원하지 않는 미디어 타입입니다.")
    ,MISSING_PARAMETER(7006, "필수 파라미터가 누락되었습니다.")
    ,VALIDATION_ERROR(7007, "유효성 검사 오류입니다.");

    private int code;

    private String message;

    private HttpStatus status;

    StatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    StatusEnum(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}

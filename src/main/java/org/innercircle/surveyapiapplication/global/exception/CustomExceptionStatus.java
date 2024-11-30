package org.innercircle.surveyapiapplication.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.global.handler.response.ResponseStatus;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus implements ResponseStatus {

    // survey exception
    NOT_FOUND_SURVEY(HttpStatus.NOT_FOUND, 2500, "해당 설문조사는 존재하지 않습니다."),
    QUESTION_SIZE_FULL(HttpStatus.BAD_REQUEST, 2501, "설문항목은 최대 10개까지 가능합니다."),

    // question exception
    NOT_FOUND_QUESTION(HttpStatus.NOT_FOUND, 2600, "해당 설문항목은 존재하지 않습니다."),
    NOT_FOUND_QUESTION_FORMAT(HttpStatus.NOT_FOUND, 2601, "해당 설문항목 양식은 존재하지 않습니다."),

    // answer exception
    NOT_FOUND_QUESTION_OPTION(HttpStatus.NOT_FOUND, 2700, "해당 답변은 선택지에 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

}

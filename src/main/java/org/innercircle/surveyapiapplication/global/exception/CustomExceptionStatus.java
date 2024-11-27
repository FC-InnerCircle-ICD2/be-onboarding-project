package org.innercircle.surveyapiapplication.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.global.handler.response.ResponseStatus;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus implements ResponseStatus {

    // survey exception
    QUESTION_SIZE_FULL(HttpStatus.BAD_REQUEST, 2500, "설문항목은 최대 10개까지 가능합니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

}

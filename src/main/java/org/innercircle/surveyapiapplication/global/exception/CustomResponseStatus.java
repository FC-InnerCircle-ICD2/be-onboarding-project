package org.innercircle.surveyapiapplication.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.global.handler.response.ResponseStatus;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomResponseStatus implements ResponseStatus {

    // success
    SUCCESS(HttpStatus.OK, 1000, "요청 정상 처리되었습니다."),

    // survey exception
    NOT_FOUND_SURVEY(HttpStatus.NOT_FOUND, 2500, "해당 설문조사는 존재하지 않습니다."),
    QUESTION_SIZE_FULL(HttpStatus.BAD_REQUEST, 2501, "설문항목은 최대 10개까지 가능합니다."),

    // survey item exception
    NOT_FOUND_SURVEY_ITEM(HttpStatus.NOT_FOUND, 2600, "해당 설문항목은 존재하지 않습니다."),
    NOT_FOUND_SURVEY_ITEM_FORMAT(HttpStatus.NOT_FOUND, 2601, "해당 설문항목 양식은 존재하지 않습니다."),

    // answer exception
    NOT_FOUND_SURVEY_SUBMISSION_IN_ITEM_OPTION(HttpStatus.NOT_FOUND, 2700, "해당 답변은 선택지에 존재하지 않습니다."),
    NOT_MATCH_SURVEY_ITEM_SUBMISSION_TYPE(HttpStatus.BAD_REQUEST, 2701, "해당 답변은 설문항목과 다른형태의 답변입니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

}

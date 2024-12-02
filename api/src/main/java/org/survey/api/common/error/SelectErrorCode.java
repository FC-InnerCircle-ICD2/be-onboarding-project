package org.survey.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum SelectErrorCode implements ErrorCode{

    NON_EXIST_REQUEST(HttpStatus.BAD_REQUEST.value(),  411, "선택 리스트에 없는 항목 요청"),
    DUPLICATE_REQUEST(HttpStatus.BAD_REQUEST.value(),  412, "중복된 항목 요청")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}

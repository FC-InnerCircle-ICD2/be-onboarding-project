package org.innercircle.surveyapiapplication.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final CustomExceptionStatus status;

}

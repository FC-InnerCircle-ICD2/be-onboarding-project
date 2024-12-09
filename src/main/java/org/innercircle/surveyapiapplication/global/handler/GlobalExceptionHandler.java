package org.innercircle.surveyapiapplication.global.handler;

import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.handler.response.CustomApiResponse;
import org.innercircle.surveyapiapplication.global.handler.response.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleQuestionSizeFullException(CustomException e) {
        ResponseStatus status = e.getStatus();
        return ResponseEntity.status(status.getHttpStatus())
            .body(CustomApiResponse.onError(status));
    }

}

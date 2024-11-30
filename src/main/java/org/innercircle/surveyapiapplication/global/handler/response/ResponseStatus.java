package org.innercircle.surveyapiapplication.global.handler.response;

import org.springframework.http.HttpStatus;

public interface ResponseStatus {

    HttpStatus getHttpStatus();

    int getCode();

    String getMessage();

}

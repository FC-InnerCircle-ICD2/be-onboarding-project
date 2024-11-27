package org.brinst.surveyapi.config;

import org.brinst.surveycommon.config.GlobalException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(exception = GlobalException.class)
	public ResponseEntity<Object> handleGlobalException(GlobalException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(e.getErrorCode().getCode()));
	}
}

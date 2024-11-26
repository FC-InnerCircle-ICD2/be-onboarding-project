package com.innercircle.command.config.http;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

	@ExceptionHandler({
			IllegalArgumentException.class,
			IllegalStateException.class,
			ConstraintViolationException.class,
			MethodArgumentNotValidException.class
	})
	public ResponseEntity<?> handleBadRequestException(Exception e) {
		if (e instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
			return new ResponseEntity<>(
					methodArgumentNotValidException.getBindingResult().getAllErrors().getFirst().getDefaultMessage(),
					HttpStatus.BAD_REQUEST
			);
		}
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}

package com.survey.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SurveyApiException extends RuntimeException {
	private static final long serialVersionUID = 1645224998008042785L;

	private int code;
	private String message;

	//
	public SurveyApiException(int code, String message) {
		this.code = code;
		this.message = message;
	}
}

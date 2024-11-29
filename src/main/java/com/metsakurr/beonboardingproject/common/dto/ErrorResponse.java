package com.metsakurr.beonboardingproject.common.dto;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import lombok.Getter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    private final List<FieldError> fieldErrors;

    private ErrorResponse(ResponseCode resultCode, List<FieldError> fieldErrors) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.fieldErrors = fieldErrors;
    }

    public static ErrorResponse of(BindingResult bindingResult) {
        return new ErrorResponse(
                ResponseCode.NOT_VALID_DATA,
                FieldError.of(bindingResult)
        );
    }

    public static ErrorResponse of(HttpMessageNotReadableException ex) {
        return new ErrorResponse(
                ResponseCode.NOT_VALID_TYPE,
                FieldError.of(ex)
        );
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final Object rejectedValue;
        private final String reason;

        private FieldError(String field, Object rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

        public static List<FieldError> of(HttpMessageNotReadableException ex) {
            if (ex.getCause() instanceof JsonMappingException jsonMappingException) {
                return jsonMappingException.getPath().stream()
                        .map(reference -> new FieldError(
                                reference.getFieldName(),
                                null,
                                ex.getMessage()
                        ))
                        .collect(Collectors.toList());
            }
            return null;
        }

    }
}
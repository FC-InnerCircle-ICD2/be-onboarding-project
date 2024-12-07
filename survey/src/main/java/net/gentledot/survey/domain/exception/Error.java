package net.gentledot.survey.domain.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Error {
    private final String errorCode;
    private final String errorMessage;

    public Error(ServiceError error) {
        this.errorCode = error.getCode();
        this.errorMessage = error.getMessage();
    }
}

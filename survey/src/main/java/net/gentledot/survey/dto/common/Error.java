package net.gentledot.survey.dto.common;

import lombok.Getter;
import lombok.ToString;
import net.gentledot.survey.exception.ServiceError;

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

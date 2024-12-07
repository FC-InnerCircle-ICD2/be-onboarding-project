package org.survey.common.exception;

import lombok.Getter;
import org.survey.common.error.ErrorCode;

@Getter
public class ApiException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String errorDescription;

    public ApiException(ErrorCode errorCode){
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorDescription = errorCode.getDescription();
    }

    public ApiException(ErrorCode errorCode, String errorDescription){
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public ApiException(ErrorCode errorCode, Throwable tx){
        super(tx);
        this.errorCode = errorCode;
        this.errorDescription = errorCode.getDescription();
    }

    public ApiException(ErrorCode errorCode, Throwable tx, String errorDescription){
        super(tx);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
}

package com.onboarding.core.global.exception;

import com.onboarding.core.global.exception.enums.ErrorCode;

public class CustomException extends RuntimeException{
  private final ErrorCode errorCode;

  public CustomException(String s, ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}

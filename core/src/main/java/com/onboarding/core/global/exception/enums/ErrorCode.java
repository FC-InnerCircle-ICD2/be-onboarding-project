package com.onboarding.core.global.exception.enums;

public enum ErrorCode {
  // 400 Bad Request
  INVALID_INPUT_VALUE("E400", "Invalid input value"),
  MUST_BE_CHOICES("E400", "Choices must be provided for single or multiple choice questions."),
  INVALID_TYPE_VALUE("E400", "Invalid type value"),
  INVALID_REQUEST("E400", "Invalid request"),
  INVALID_CHOICES("E400", "Invalid Choices" ),


  // 404 Not Found
  ENTITY_NOT_FOUND("E404", "Entity not found"),
  SURVEY_NOT_FOUND("E404", "Survey not found"),
  QUESTION_NOT_FOUND("E404", "Question not found"),
  MISSING_REQUIRED_ANSWER("E404", "Missing required answer"),

  // 409 Conflict
  ENTITY_ALREADY_EXISTS("E409", "Entity already exists"),
  SURVEY_CANT_MORE_THAN_10("E409", "A survey cannot have more than 10 questions."),
  CONCURRENT_MODIFICATION("E409", "Concurrent modification"),

  // 403 Forbidden
  HANDLE_ACCESS_DENIED("E403", "Access is denied"),

  // 405 Method Not Allowed
  METHOD_NOT_ALLOWED("E405", "Method not allowed"),

  // 500 Internal Server Error
  INTERNAL_SERVER_ERROR("E500", "Internal server error");

  private final String code;
  private final String message;

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}

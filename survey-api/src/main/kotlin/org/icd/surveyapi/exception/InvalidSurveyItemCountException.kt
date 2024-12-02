package org.icd.surveyapi.exception

import org.springframework.http.HttpStatus

class InvalidSurveyItemCountException : ApplicationException(
    httpStatus = HttpStatus.BAD_REQUEST,
    errorCode = ErrorCode.INVALID_SURVEY_ITEM_COUNT
)

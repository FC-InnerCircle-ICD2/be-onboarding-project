package org.icd.surveyapi.exception

import org.springframework.http.HttpStatus

class NotFoundSurveyException : ApplicationException(
    httpStatus = HttpStatus.NOT_FOUND,
    errorCode = ErrorCode.NOT_FOUND_SURVEY
)

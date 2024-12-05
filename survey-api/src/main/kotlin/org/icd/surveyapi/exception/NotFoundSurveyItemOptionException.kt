package org.icd.surveyapi.exception

import org.springframework.http.HttpStatus

class NotFoundSurveyItemOptionException : ApplicationException(
    httpStatus = HttpStatus.NOT_FOUND,
    errorCode = ErrorCode.NOT_FOUND_SURVEY_ITEM_OPTION,
)
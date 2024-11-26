package org.icd.surveyapi.exception

import org.springframework.http.HttpStatus

class MissingRequiredFieldException(fieldName: String): ApplicationException(
    httpStatus = HttpStatus.BAD_REQUEST,
    errorCode = ErrorCode.MISSING_REQUIRED_FIELD,
    errorMessage = "${ErrorCode.MISSING_REQUIRED_FIELD.errorMessage} - [$fieldName]"
) {
}
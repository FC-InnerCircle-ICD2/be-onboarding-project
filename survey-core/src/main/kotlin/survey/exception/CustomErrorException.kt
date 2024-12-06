package survey.exception

sealed class CustomErrorException(
    val errorCode: CustomHttpStatusCode,
    override val message: String?,
) : RuntimeException(message) {
    class NotFoundException(
        message: String = "데이터를 찾을 수 없습니다.",
        errorCode: CustomHttpStatusCode,
    ) : CustomErrorException(
            errorCode = errorCode,
            message = message,
        )

    class SelectOptionExistAnswerOption(
        message: String = "Single / Multi 선택의 항목 이지만 답변 Answer 가 존재 합니다.",
        errorCode: CustomHttpStatusCode,
    ) : CustomErrorException(
            errorCode = errorCode,
            message = message,
        )

    class SelectedOptionNotExist(
        message: String = "Single / Multi 선택의 항목 이지만 선택 옵션이 존재 하지 않습니다.",
        errorCode: CustomHttpStatusCode,
    ) : CustomErrorException(
            errorCode = errorCode,
            message = message,
        )

    class OverShortAnswerTypeLength(
        message: String = "Short Answer 타입 이지만 글자 수가 초과 하였습니다.",
        errorCode: CustomHttpStatusCode,
    ) : CustomErrorException(
            errorCode = errorCode,
            message = message,
        )

    class SelectOptionExistString(
        message: String = "Short Answer 타입 이지만 선택 한 옵션 값이 존재 합니다.",
        errorCode: CustomHttpStatusCode,
    ) : CustomErrorException(
            errorCode = errorCode,
            message = message,
        )

    class RequiredAnswer(
        message: String = "Short Answer 타입 이지만 선택 한 옵션 값이 존재 합니다.",
        errorCode: CustomHttpStatusCode,
    ) : CustomErrorException(
            errorCode = errorCode,
            message = message,
        )

    class NotMatchSurveyFormItem(
        message: String = "해당 SurveyForm 과 이이템 갯수가 다릅니다.",
        errorCode: CustomHttpStatusCode,
    ) : CustomErrorException(
            errorCode = errorCode,
            message = message,
        )

    class InvalidAnswerFormat(
        message: String = "해당 답변 포맷이 유효하지 않습니다..",
        errorCode: CustomHttpStatusCode,
    ) : CustomErrorException(
            errorCode = errorCode,
            message = message,
        )
}

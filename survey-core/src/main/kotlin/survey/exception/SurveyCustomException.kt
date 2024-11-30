package survey.exception

sealed class SurveyCustomException private constructor(
    override val message: String,
    override val cause: Throwable?,
) : RuntimeException(message, cause) {
    class SurveyExceptionSurvey private constructor(
        message: String = "Runtime Exception occurred.",
        cause: Throwable? = null,
    ) : SurveyCustomException(message = message, cause = cause)
}

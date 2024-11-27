package survey.exception

sealed class CustomException private constructor(
    override val message: String,
    override val cause: Throwable?,
): RuntimeException(message, cause) {

    class SurveyException(
        message: String = "Runtime Exception occurred.",
        cause: Throwable? = null
    ) : CustomException(message = message, cause = cause)
}
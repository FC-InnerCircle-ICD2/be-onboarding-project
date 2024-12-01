package survey.exception

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class SurveyExceptionFormat private constructor (
    val errorMessage: String,
    val errorCode: String,
    val timestamp: LocalDateTime,
    val detail: String? = null,
) {
    companion object {
        fun of(
            exception: Exception,
            errorCode: String,
            detail: String? = null,
        ): SurveyExceptionFormat {
            return SurveyExceptionFormat(
                errorMessage = exception.message ?: exception.javaClass.simpleName,
                errorCode = errorCode,
                timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                detail = detail,
            )
        }
    }
}

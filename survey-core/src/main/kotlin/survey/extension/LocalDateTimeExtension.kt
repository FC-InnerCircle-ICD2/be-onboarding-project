package survey.extension

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeExtension {

    // TODO - 타임 포맷 형태 정하기 !
    fun LocalDateTime.toResponseDateTimeFormat() = this.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    fun LocalDate.toResponseDateFormat() = this.format(DateTimeFormatter.ISO_LOCAL_DATE)
}

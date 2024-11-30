package survey.util

import java.util.UUID

object UuidGeneratorUtil {
    fun generateUuid(): String = UUID.randomUUID().toString()
}

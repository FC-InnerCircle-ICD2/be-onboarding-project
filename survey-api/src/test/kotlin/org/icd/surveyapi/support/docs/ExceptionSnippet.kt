package org.icd.surveyapi.support.docs

import org.icd.surveyapi.exception.ApplicationException
import org.springframework.restdocs.operation.Operation
import org.springframework.restdocs.snippet.Snippet
import java.io.File
import java.io.FileWriter

class ExceptionSnippet(
    private val exceptions: List<ApplicationException>
) : Snippet {

    override fun document(operation: Operation) {
        val outputDirectory = File("build/generated-snippets", operation.name)

        val file = File(outputDirectory, "exception.adoc")
        FileWriter(file).use { writer ->
            writer.write(generateExceptionDescriptionsAsciiDocTable(exceptions))
        }
    }

    private fun generateExceptionDescriptionsAsciiDocTable(
        exceptions: List<ApplicationException>
    ): String {
        val sb = StringBuilder()

        sb.append("|===\n")
        sb.append("| HTTP Status | ErrorCode | ErrorMessage \n\n")

        for (exception in exceptions) {
            sb.append("| ${exception.httpStatus}\n")
            sb.append("| ${exception.errorCode}\n")
            sb.append("| ${exception.errorMessage}\n")
        }

        sb.append("|===\n")

        return sb.toString()
    }
}
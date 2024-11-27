package org.icd.surveyapi.support.filter

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.http.MediaType
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.net.URLDecoder
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

class CachingRequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private var contents = ByteArrayOutputStream()
    private var parameterMap = LinkedHashMap<String, Array<String>>()

    init {
        mergeRequestParameterMap()
        run {
            if (request.contentType != MediaType.APPLICATION_FORM_URLENCODED_VALUE) {
                return@run
            }

            val contentBytes = contents.toByteArray()

            if (contentBytes.isEmpty()) {
                return@run
            }

            val contentString = String(contentBytes, getCharset())
            contentString.split("&").forEach {
                val part = it.split("=")
                val key = part.first()
                val value = URLDecoder.decode(part.drop(1).first(), getCharset())

                if (value.isNotBlank()) {
                    var parameterValue = parameterMap.getOrDefault(key, arrayOf())
                    parameterValue += value
                    parameterMap[part.first()] = parameterValue
                }
            }
        }
    }

    override fun getInputStream(): ServletInputStream {
        IOUtils.copy(request.inputStream, contents)

        return object : ServletInputStream() {
            private var buffer = ByteArrayInputStream(contents.toByteArray())

            override fun read(): Int = buffer.read()

            override fun isFinished(): Boolean = buffer.available() == 0

            override fun isReady(): Boolean = true

            override fun setReadListener(listener: ReadListener?) = throw RuntimeException("Not Implemented")
        }
    }

    override fun getParameterMap(): MutableMap<String, Array<String>> {
        return parameterMap
    }

    override fun getParameter(name: String?): String {
        return parameterMap[name]?.get(0) ?: ""
    }

    override fun getParameterNames(): Enumeration<String> {
        return object : Enumeration<String> {
            val names = parameterMap.keys.toTypedArray()
            var index = 0

            override fun hasMoreElements(): Boolean = index < names.size

            override fun nextElement(): String = names[index++]
        }
    }

    override fun getParameterValues(name: String?): Array<String> {
        return parameterMap[name] ?: arrayOf()
    }

    private fun mergeRequestParameterMap() {
        request.parameterMap.entries.forEach {
            val key = it.key
            val value = it.value

            var parameterValue = parameterMap.getOrDefault(key, arrayOf())
            parameterValue += value
            parameterMap[it.key] = parameterValue
        }
    }

    private fun getCharset(): Charset {
        return if (request.characterEncoding.isNullOrBlank()) {
            Charset.forName(request.characterEncoding)
        } else {
            StandardCharsets.UTF_8
        }
    }
}
package org.icd.surveyapi.support.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.util.ContentCachingResponseWrapper
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*

class GlobalLogInterceptor : HandlerInterceptor {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val traceId = UUID.randomUUID().toString().substring(0, 8)
        request.setAttribute("traceId", traceId)

        val startTime = Instant.now().toEpochMilli()
        request.setAttribute("startTime", startTime)

        log.info(
            "[{}] - {} {} {} {}",
            traceId,
            request.getHeader("host"),
            request.method,
            request.getRequestUri(),
            getBodyString(request.inputStream.readBytes()),
        )

        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        val traceId = request.getAttribute("traceId") ?: "no-traceId"
        val cachingResponse = response as ContentCachingResponseWrapper

        log.info(
            "[{}] - [{}] {} {} [ {}ms ]",
            traceId,
            response.status,
            response.contentType,
            getBodyString(cachingResponse.contentInputStream.readBytes()),
            getExecutionElapsedTime(request),
        )
    }

    private fun getBodyString(bodyBytes: ByteArray): String {
        return String(bodyBytes, StandardCharsets.UTF_8)
            .replace(Regex("\n|\\s"), "")
    }

    private fun getExecutionElapsedTime(request: HttpServletRequest): Long {
        val startTime = request.getAttribute("startTime") ?: 0L
        val endTime = Instant.now().toEpochMilli()

        if (startTime == 0) {
            return 0
        }

        return endTime - startTime.toString().toLong()
    }

    private fun HttpServletRequest.getRequestUri(): String {
        return if (!this.queryString.isNullOrBlank()) {
            "${this.requestURI}?${this.queryString}"
        } else {
            this.requestURI
        }
    }
}
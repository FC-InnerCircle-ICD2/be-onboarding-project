package org.icd.surveyapi.support.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper

@Component
class CachingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val cachingRequestWrapper = CachingRequestWrapper(request)
        val cachingResponseWrapper = ContentCachingResponseWrapper(response)

        filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper)
        cachingResponseWrapper.copyBodyToResponse()
    }
}
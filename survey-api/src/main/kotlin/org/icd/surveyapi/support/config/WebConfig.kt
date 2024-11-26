package org.icd.surveyapi.support.config

import org.icd.surveyapi.support.interceptor.GlobalLogInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(GlobalLogInterceptor())
            .addPathPatterns("/**")
    }
}
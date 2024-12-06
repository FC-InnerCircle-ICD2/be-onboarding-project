package com.ic.surveyapi.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean

// @Configuration
class OpenApiConfig {
    @Bean
    fun customOpenApi(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("Survey API Documentation")
                    .version("1.0.0")
                    .description("Survey Application Docs"),
            )
}

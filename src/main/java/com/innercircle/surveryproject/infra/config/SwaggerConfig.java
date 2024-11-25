package com.innercircle.surveryproject.infra.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "Survey", version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

}

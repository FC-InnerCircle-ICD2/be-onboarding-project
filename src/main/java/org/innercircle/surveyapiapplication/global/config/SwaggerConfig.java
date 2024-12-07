package org.innercircle.surveyapiapplication.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPIConfig() {
        return new OpenAPI()
            .components(new Components())
            .info(new Info()
                .title("Survey API")
                .version("1.0.0")
                .description("This is the API documentation for our survey service.")
            );
    }

}

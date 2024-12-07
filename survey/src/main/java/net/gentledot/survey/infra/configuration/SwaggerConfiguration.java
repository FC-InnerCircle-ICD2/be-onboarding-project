package net.gentledot.survey.infra.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .version("0.0.1")
                        .title("설문조사 서비스")
                        .description("기능 요구사항인 설문조사 생성, 수정, 응답 제출, 응답 조회 API에 대한 Document 입니다.")
                );
    }
}

package net.gentledot.survey.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;

@TestComponent
public class ObjectMapperComponent {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

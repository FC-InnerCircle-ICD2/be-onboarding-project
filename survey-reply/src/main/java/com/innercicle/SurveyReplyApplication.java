package com.innercicle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@EnableJpaAuditing
@SpringBootApplication
@ConfigurationPropertiesScan
public class SurveyReplyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyReplyApplication.class, args);
    }

}
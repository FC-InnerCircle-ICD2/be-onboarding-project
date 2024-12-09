package com.innercircle.surveryproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SurveryProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveryProjectApplication.class, args);
    }

}

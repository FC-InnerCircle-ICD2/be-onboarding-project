package com.onboarding.survey.survey.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.onboarding.survey.survey.entity")
@EnableJpaRepositories(basePackages = "com.onboarding.survey.survey.repository")
public class SurveyConfig {

}

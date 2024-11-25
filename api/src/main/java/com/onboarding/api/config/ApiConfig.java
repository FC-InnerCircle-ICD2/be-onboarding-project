package com.onboarding.api.config;

import com.onboarding.survey.survey.config.SurveyConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SurveyConfig.class)
public class ApiConfig {

}

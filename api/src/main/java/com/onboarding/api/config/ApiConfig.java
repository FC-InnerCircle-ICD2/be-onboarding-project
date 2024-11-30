package com.onboarding.api.config;

import com.onboarding.core.global.config.SwaggerConfig;
import com.onboarding.response.config.ResponseConfig;
import com.onboarding.survey.config.SurveyConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SurveyConfig.class, SwaggerConfig.class, ResponseConfig.class})
public class ApiConfig {

}

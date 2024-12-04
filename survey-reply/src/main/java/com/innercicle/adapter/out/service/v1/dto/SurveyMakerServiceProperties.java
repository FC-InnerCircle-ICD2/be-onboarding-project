package com.innercicle.adapter.out.service.v1.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service.survey-maker")
public class SurveyMakerServiceProperties {

    private String url;

}

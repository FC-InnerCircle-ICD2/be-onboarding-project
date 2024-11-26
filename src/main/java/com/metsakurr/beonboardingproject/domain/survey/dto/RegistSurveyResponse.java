package com.metsakurr.beonboardingproject.domain.survey.dto;

import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import lombok.Getter;

@Getter
public class RegistSurveyResponse {
    long idx;
    String name;
    String description;

    public RegistSurveyResponse(Survey survey) {
        this.idx = survey.getIdx();
        this.name = survey.getName();
        this.description = survey.getDescription();
    }
}

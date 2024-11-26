package com.metsakurr.beonboardingproject.domain.survey.dto;

import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SurveyRequest {
    private String name;
    private String description;
    private List<QuestionRequest> questions;

    public Survey toEntity() {
        return Survey.builder()
                .name(name)
                .description(description)
                .build();
    }
}

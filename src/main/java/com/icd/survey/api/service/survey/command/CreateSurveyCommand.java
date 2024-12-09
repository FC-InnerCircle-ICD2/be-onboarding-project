package com.icd.survey.api.service.survey.command;

import com.icd.survey.api.entity.survey.dto.SurveyDto;

import java.util.List;

public record CreateSurveyCommand(
        String surveyName,
        String surveyDescription,
        String ipAddress,
        List<SurveyItemCommand> surveyItemCommandList
) {
    public SurveyDto createSurveyDtoRequest() {
        return SurveyDto
                .builder()
                .ipAddress(ipAddress)
                .surveyName(surveyName)
                .surveyDescription(surveyDescription)
                .build();
    }
}

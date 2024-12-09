package com.icd.survey.api.service.survey.command;

import com.icd.survey.api.entity.survey.dto.SurveyDto;
import com.icd.survey.common.CommonUtils;

import java.util.List;

public record UpdateSurveyCommand(
        Long surveySeq,
        String surveyName,
        String surveyDescription,
        String ipAddress,
        List<SurveyItemCommand> surveyItemCommandList
) {
    public SurveyDto createSurveyDtoRequest() {
        return SurveyDto
                .builder()
                .surveySeq(surveySeq)
                .surveyName(surveyName)
                .surveyDescription(surveyDescription)
                .ipAddress(ipAddress)
                .build();
    }
}

package com.icd.survey.api.service.survey.command;

import com.icd.survey.api.controller.survey.request.SurveyItemRequest;

import java.util.List;

public record SubmitSurveyCommand(
        Long surveySeq,
        List<SurveyItemCommand> surveyItemList
) {
}

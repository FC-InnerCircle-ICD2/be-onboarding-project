package com.innercircle.command.application.survey;

import com.innercircle.command.domain.Identifier;
import com.innercircle.command.domain.survey.SurveyService;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class SurveyCommandHandler {

	private final SurveyService surveyService;

	public SurveyCommandHandler(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	@ServiceActivator(inputChannel = "CreateSurveyCommand")
	public Identifier handle(CreateSurveyCommand command) {
		return surveyService.createSurvey(command.getName(), command.getDescription(), command.getQuestionInputs());
	}

	@ServiceActivator(inputChannel = "UpdateSurveyCommand")
	public Identifier handle(UpdateSurveyCommand command) {
		return surveyService.updateSurvey(command.getSurveyId(), command.getQuestionUpdateInputs());
	}

	@ServiceActivator(inputChannel = "CreateSurveyResponseCommand")
	public Identifier handle(CreateSurveyResponseCommand command) {
		return surveyService.createResponse(command.getSurveyId(), command.getResponseInputs());
	}
}

package com.innercircle.command.interfaces;

import com.innercircle.common.application.CommandGateway;
import com.innercircle.command.application.survey.CreateSurveyCommand;
import com.innercircle.command.application.survey.CreateSurveyResponseCommand;
import com.innercircle.command.application.survey.UpdateSurveyCommand;
import com.innercircle.command.domain.Identifier;
import com.innercircle.command.interfaces.request.CreateSurveyRequest;
import com.innercircle.command.interfaces.request.CreateSurveyResponseRequest;
import com.innercircle.command.interfaces.request.UpdateSurveyRequest;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CommandInterface
public class SurveyController {

	private final CommandGateway commandGateway;

	public SurveyController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@PostMapping("/surveys")
	public Identifier createSurvey(@RequestBody CreateSurveyRequest request) {
		return commandGateway.publish(new CreateSurveyCommand(request.name(), request.description(), request.questionInputs()));
	}

	@PatchMapping("/surveys/{surveyId}")
	public Identifier updateSurvey(@PathVariable String surveyId, @RequestBody UpdateSurveyRequest request) {
		return commandGateway.publish(new UpdateSurveyCommand(surveyId, request.updateInputs()));
	}

	@PostMapping("/surveys/{surveyId}/responses")
	public Identifier createSurveyResponse(@PathVariable String surveyId, @RequestBody CreateSurveyResponseRequest request) {
		return commandGateway.publish(new CreateSurveyResponseCommand(surveyId, request.responseInputs()));
	}
}

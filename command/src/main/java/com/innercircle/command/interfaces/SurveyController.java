package com.innercircle.command.interfaces;

import com.innercircle.command.application.survey.SurveyService;
import com.innercircle.command.domain.Identifier;
import com.innercircle.command.interfaces.request.CreateSurveyRequest;
import com.innercircle.command.interfaces.request.CreateSurveyResponseRequest;
import com.innercircle.command.interfaces.request.UpdateSurveyRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CommandInterface
public class SurveyController {

	private final SurveyService surveyService;

	public SurveyController(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	@PostMapping("/create-survey")
	public Identifier createSurvey(@RequestBody CreateSurveyRequest request) {
		return surveyService.create(request.name(), request.description(), request.questionInputs());
	}

	@PostMapping("/update-survey/{id}")
	public Identifier updateSurvey(@PathVariable String id, @RequestBody UpdateSurveyRequest request) {
		return surveyService.updateResponse(id, request.updateInputs());
	}

	@PostMapping("/surveys/{id}/responses")
	public Identifier createSurveyResponse(@PathVariable String id, @RequestBody CreateSurveyResponseRequest request) {
		return surveyService.createResponse(id, request.responseInputs());
	}
}

package com.innercircle.command.interfaces;

import com.innercircle.command.application.survey.SurveyService;
import com.innercircle.command.interfaces.request.CreateSurveyRequest;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CommandInterface
public class SurveyController {

	private final SurveyService surveyService;

	public SurveyController(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	@PostMapping("create-survey")
	public UUID createSurvey(@RequestBody CreateSurveyRequest request) {
		return surveyService.create(request.name(), request.description(), request.questionInputs());
	}
}

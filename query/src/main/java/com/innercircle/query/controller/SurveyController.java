package com.innercircle.query.controller;

import com.innercircle.query.controller.dto.SurveyDto;
import com.innercircle.query.facade.SurveyFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyController {

	private final SurveyFacade surveyFacade;

	public SurveyController(SurveyFacade surveyFacade) {
		this.surveyFacade = surveyFacade;
	}

	@GetMapping("/surveys/{id}")
	public SurveyDto getSurvey(@PathVariable String id) {
		return surveyFacade.getSurvey(id);
	}
}

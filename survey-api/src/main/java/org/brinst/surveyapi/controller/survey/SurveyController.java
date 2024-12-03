package org.brinst.surveyapi.controller.survey;

import org.brinst.surveycore.survey.dto.SurveyDTO;
import org.brinst.surveycore.survey.service.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SurveyController {
	private final SurveyService surveyService;

	@GetMapping("/api/v1/survey/{surveyId}")
	public ResponseEntity<SurveyDTO.ResDTO> getSurveyInfoById(@PathVariable("surveyId") Long surveyId) {
		return new ResponseEntity<>(surveyService.getSurveyById(surveyId), HttpStatus.OK);
	}

	@PutMapping("/api/v1/survey/{surveyId}")
	public ResponseEntity<HttpStatus> modifySurveyInfoById(
		@PathVariable("surveyId") Long surveyId,
		@RequestBody SurveyDTO.ReqDTO surveyDTO
	) {
		surveyService.modifySurvey(surveyId, surveyDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/api/v1/survey")
	public ResponseEntity<HttpStatus> registerSurvey(@RequestBody SurveyDTO.ReqDTO surveyDTO) {
		surveyService.registerSurvey(surveyDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}

package org.brinst.surveyapi.controller.answer;

import java.util.List;

import org.brinst.surveycore.answer.dto.AnswerDTO;
import org.brinst.surveycore.answer.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AnswerController {
	private final AnswerService answerService;

	@PostMapping("/api/v1/survey/{surveyId}")
	public ResponseEntity<HttpStatus> answerSurvey(
		@PathVariable("surveyId") Long surveyId,
		@RequestBody List<AnswerDTO.ReqDTO> answerDTO
	) {
		answerService.answerSurvey(surveyId, answerDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/api/v1/survey/{surveyId}/answers")
	public ResponseEntity<List<AnswerDTO.ResDTO>> getSurveyAnswersBySurveyId(
		@PathVariable("surveyId") Long surveyId,
		@RequestParam(value = "questionName", required = false) String questionName,
		@RequestParam(value = "answerValue", required = false) String answerValue) {
		return new ResponseEntity<>(answerService.getAnswerBySurveyIdAndCondition(surveyId, questionName, answerValue), HttpStatus.OK);
	}
}

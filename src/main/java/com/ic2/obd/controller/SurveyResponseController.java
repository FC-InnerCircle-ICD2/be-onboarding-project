package com.ic2.obd.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ic2.obd.dto.ResponseDto;
import com.ic2.obd.service.SurveyResponseService;

@RestController
@RequestMapping("/surveys/{surveyId}/responses")
public class SurveyResponseController {
	
	private final SurveyResponseService surveyResponseService;

    public SurveyResponseController(SurveyResponseService surveyResponseService) {
        this.surveyResponseService = surveyResponseService;
    }

    /**
     * 설문조사 응답 제출
     * @param surveyId 설문조사 ID
     * @param responseDto 응답 데이터
     * @return 성공 메시지
     */
    @PostMapping
    public ResponseEntity<String> submitResponse(
            @PathVariable("surveyId") Long surveyId,
            @RequestBody ResponseDto responseDto) {
        surveyResponseService.submitResponse(surveyId, responseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("응답이 성공적으로 저장되었습니다.");
    }
}

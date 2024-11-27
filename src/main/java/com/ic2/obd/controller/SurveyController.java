package com.ic2.obd.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ic2.obd.domain.Survey;
import com.ic2.obd.service.SurveyService;

/**
 * 설문조사와 관련된 HTTP 요청을 처리하는 컨트롤러 클래스.
 */
@RestController
@RequestMapping("/surveys")
public class SurveyController {
	private final SurveyService surveyService;

	/**
     * SurveyController의 생성자.
     * SurveyService를 의존성 주입 받아 사용.
     *
     * @param surveyService SurveyService 구현체
     */
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }
    
    /**
     * 새로운 설문조사를 생성하는 API 엔드포인트.
     *
     * @param survey 요청 본문(JSON)으로 전달된 설문조사 객체
     * @return 생성된 설문조사 객체와 HTTP 상태 코드 201(Created)
     */
    @PostMapping
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
    	// SurveyService를 호출하여 설문조사를 생성하고 저장
        Survey createdSurvey = surveyService.createSurvey(survey);
        // HTTP 상태 코드 201(Created)와 함께 생성된 설문조사 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSurvey);
    }
}

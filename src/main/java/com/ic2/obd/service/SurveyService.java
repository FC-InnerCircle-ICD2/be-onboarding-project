package com.ic2.obd.service;

import org.springframework.stereotype.Service;

import com.ic2.obd.domain.Question;
import com.ic2.obd.domain.Survey;
import com.ic2.obd.repository.SurveyRepository;

/**
 * 설문조사와 관련된 비즈니스 로직을 처리하는 서비스 클래스.
 */
@Service
public class SurveyService {
	
	private final SurveyRepository surveyRepository;
	
	/**
     * SurveyRepository를 의존성 주입 받아 사용.
     */
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }
    
    /**
     * 설문조사를 생성하고 데이터베이스에 저장합니다.
     *
     * @param survey 생성할 설문조사 객체
     * @return 저장된 설문조사 객체
     */
    public Survey createSurvey(Survey survey) {
    	// 각 질문에 설문조사를 명시적으로 연결
    	for(Question question : survey.getQuestions()) {
    		question.setSurvey(survey);
    	}
    	// 설문조사 저장
        return surveyRepository.save(survey);
    }
}

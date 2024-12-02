package com.ic2.obd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ic2.obd.domain.Question;
import com.ic2.obd.domain.Survey;
import com.ic2.obd.repository.QuestionRepository;
import com.ic2.obd.repository.SurveyRepository;

/**
 * 설문조사와 관련된 비즈니스 로직을 처리하는 서비스 클래스.
 */
@Service
public class SurveyService {
	
	private final SurveyRepository surveyRepository;
	private final QuestionRepository questionRepository;
	
	/**
     * SurveyRepository를 의존성 주입 받아 사용.
     */
    public SurveyService(SurveyRepository surveyRepository, QuestionRepository questionRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
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
    
    /**
     * 설문조사를 수정하는 메서드
     * @param id 수정할 설문조사의 ID
     * @param updatedSurvey 수정 요청 데이터
     * @return 수정된 설문조사 객체
     */
    public Survey updateSurvey(Long id, Survey updatedSurvey) {
    	// 수정하려는 설문조사를 ID로 찾기
    	Survey existingSurvey = surveyRepository.findById(id).orElse(null);

    	if (existingSurvey == null) {
    	    throw new IllegalArgumentException("설문조사를 찾을 수 없습니다.");
    	}
        
        // 설문조사의 기본 정보(제목, 설명)를 수정
        existingSurvey.setSurveyName(updatedSurvey.getSurveyName());
        existingSurvey.setSurveyDescription(updatedSurvey.getSurveyDescription());
        
        // 기존 설문조사의 질문 목록 가져오기
        List<Question> existingQuestions = existingSurvey.getQuestions();
        
        // 요청에서 받은 질문 목록 가져오기
        List<Question> updatedQuestions = updatedSurvey.getQuestions();
        
        // 요청에서 받은 질문 처리
        for (Question updatedQuestion : updatedQuestions) {
            if (updatedQuestion.getId() == null) {
                // 기존에 없는 질문일 경우 새로운 질문은 추가
            	 updatedQuestion.setSurvey(existingSurvey); // 연관 관계 설정
                 Question savedQuestion = questionRepository.save(updatedQuestion); // 저장
                 existingSurvey.addQuestion(savedQuestion); // Survey에 추가
            } else {
                // 기존 질문은 ID를 기준으로 찾아 수정
                for (Question existingQuestion : existingQuestions) {
                    if (existingQuestion.getId().equals(updatedQuestion.getId())) {
                        existingQuestion.setQuestionName(updatedQuestion.getQuestionName());
                        existingQuestion.setQuestionDescription(updatedQuestion.getQuestionDescription());
                        existingQuestion.setInputType(updatedQuestion.getInputType());
                        existingQuestion.setRequired(updatedQuestion.isRequired());
                        existingQuestion.setOptions(updatedQuestion.getOptions());
                        break;
                    }
                }
            }
        }

        // 요청에 없는 질문은 삭제
        // 기존 질문 중 삭제할 항목 찾기
        List<Question> questionsToRemove = new ArrayList<>();

        for (Question existingQuestion : existingQuestions) {
            boolean found = false;
            for (Question updatedQuestion : updatedQuestions) {
                if (updatedQuestion.getId() != null && updatedQuestion.getId().equals(existingQuestion.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                questionsToRemove.add(existingQuestion); // 삭제 대상 추가
            }
        }

        // 삭제할 항목 제거
        for (Question questionToRemove : questionsToRemove) {
            existingQuestions.remove(questionToRemove);
        }

        // 수정된 설문조사를 데이터베이스에 저장
        return surveyRepository.save(existingSurvey);
        
    }
}

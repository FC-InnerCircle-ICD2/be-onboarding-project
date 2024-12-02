package com.ic2.obd.service;

import org.springframework.stereotype.Service;

import com.ic2.obd.domain.Question;
import com.ic2.obd.domain.Response;
import com.ic2.obd.domain.ResponseAnswer;
import com.ic2.obd.domain.Survey;
import com.ic2.obd.dto.ResponseAnswerDto;
import com.ic2.obd.dto.ResponseDto;
import com.ic2.obd.repository.QuestionRepository;
import com.ic2.obd.repository.ResponseRepository;
import com.ic2.obd.repository.SurveyRepository;

import jakarta.transaction.Transactional;

@Service
public class SurveyResponseService {
	
	private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ResponseRepository responseRepository;

    public SurveyResponseService(SurveyRepository surveyRepository,
                                 QuestionRepository questionRepository,
                                 ResponseRepository responseRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.responseRepository = responseRepository;
    }
    
    /**
     * 설문조사 응답 제출 로직
     * @param surveyId 설문조사 ID
     * @param responseDto 응답 데이터
     */
    @Transactional
    public void submitResponse(Long surveyId, ResponseDto responseDto) {
        // 설문조사 조회
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("설문조사를 찾을 수 없습니다."));

        // 응답 엔티티 생성
        Response response = new Response();
        response.setSurvey(survey);

        // 요청된 각 응답 처리
        for (ResponseAnswerDto answerDto : responseDto.getAnswers()) {
            // 질문 조회 및 유효성 검증
            Question question = questionRepository.findById(answerDto.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

            if (!survey.getQuestions().contains(question)) {
                throw new IllegalArgumentException("질문이 해당 설문조사에 포함되지 않습니다.");
            }

            // 응답 생성
            ResponseAnswer responseAnswer = new ResponseAnswer();
            responseAnswer.setQuestion(question);
            responseAnswer.setAnswer(answerDto.getAnswer());
            responseAnswer.setResponse(response);

            response.getAnswers().add(responseAnswer);
        }

        // 응답 저장
        responseRepository.save(response);
    }
}

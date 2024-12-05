package com.onboarding.beonboardingproject.service;

import com.onboarding.beonboardingproject.common.exception.InvalidException;
import com.onboarding.beonboardingproject.domain.response.Answer;
import com.onboarding.beonboardingproject.domain.response.Response;
import com.onboarding.beonboardingproject.domain.survey.Question;
import com.onboarding.beonboardingproject.domain.survey.Survey;
import com.onboarding.beonboardingproject.dto.AnswerDto;
import com.onboarding.beonboardingproject.dto.ResponseDto;
import com.onboarding.beonboardingproject.repository.QuestionRepository;
import com.onboarding.beonboardingproject.repository.ResponseRepository;
import com.onboarding.beonboardingproject.repository.SurveyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ResponseService {
    private final SurveyRepository surveyRepository;
    private final ResponseRepository responseRepository;
    private final QuestionRepository questionRepository;

    // 설문 응답 제출
    public void submitResponse(ResponseDto responseDto) {
        // 설문조사 조회
        Survey survey = surveyRepository.findById(responseDto.getSurveyId())
                .orElseThrow(() -> new InvalidException("설문조사가 존재하지 않습니다."));

        // 응답 유효성 검사
        for (AnswerDto answerDto : responseDto.getAnswers()) {
            // 질문 조회
            Question question = questionRepository.findById(answerDto.getQuestionId())
                    .orElseThrow(() -> new InvalidException("질문을 찾을 수 없습니다."));

            // 필수 응답 검사
            if (question.isRequired() && (answerDto.getAnswerValue() == null || answerDto.getAnswerValue().isEmpty())) {
                throw new InvalidException("필수 응답 항목이 누락되었습니다. [질문 ID: " + answerDto.getQuestionId() + "]");
            }

            boolean isValid = survey.getQuestions().stream()
                    .anyMatch(q -> question.getId().equals(answerDto.getQuestionId()));
            if (!isValid) {
                throw new InvalidException("설문 항목에 없는 응답이 포함되어 있습니다. [질문 ID: " + answerDto.getQuestionId() + "]");
            }
        }

        // 새로운 Response 엔티티 생성
        Response response = Response.builder()
                .survey(survey)
                .build();

        // 응답에 포함된 각 질문과 답변을 처리
        for (AnswerDto answerDto : responseDto.getAnswers()) {
            // 질문 조회
            Question question = questionRepository.findById(answerDto.getQuestionId())
                    .orElseThrow(() -> new InvalidException("질문을 찾을 수 없습니다."));

            // Answer 생성 및 설정
            Answer answer = Answer.builder()
                    .question(question)
                    .response(response)
                    .answerValue(answerDto.getAnswerValue())
                    .build();

            // Response에 Answer 추가
            response.addAnswer(answer);
        }

        // 4. 응답 저장
        responseRepository.save(response);
    }

    // 설문 응답 조회
    public List<ResponseDto> getResponses(Long surveyId) {
        // 1. 설문 조회
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new InvalidException("설문조사가 존재하지 않습니다."));

        // 2. 설문에 해당하는 모든 응답 조회
        return ResponseDto.listOf(responseRepository.findBySurvey(survey));
    }
}

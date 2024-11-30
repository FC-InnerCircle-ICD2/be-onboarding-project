package com.metsakurr.beonboardingproject.domain.survey.service;

import com.metsakurr.beonboardingproject.common.dto.ApiResponse;
import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import com.metsakurr.beonboardingproject.common.exception.ServiceException;
import com.metsakurr.beonboardingproject.domain.survey.dto.QuestionRequest;
import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyCreationResponse;
import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyRequest;
import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import com.metsakurr.beonboardingproject.domain.survey.repository.QuestionRepository;
import com.metsakurr.beonboardingproject.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    public ApiResponse<SurveyCreationResponse> create(SurveyRequest request) {
        Survey survey = request.toEntity();
        surveyRepository.save(survey);

        SurveyCreationResponse response = new SurveyCreationResponse(survey);
        return new ApiResponse<>(ResponseCode.SUCCESS, response);
    }

    public ApiResponse<SurveyCreationResponse> update(SurveyRequest request) {
        final long surveyIdx = request.getIdx();

        Survey survey = surveyRepository.findById(surveyIdx)
                .orElseThrow(() -> new ServiceException(ResponseCode.NOT_FOUND_SURVEY));

        List<Question> originalQuestions = survey.getQuestions();
        List<QuestionRequest> newQuestions = request.getQuestions();

        for (Question originalQuestion: originalQuestions) {
            boolean isDelete = true;
            for (QuestionRequest newQuestion: newQuestions) {
                if (originalQuestion.getIdx().equals(newQuestion.getIdx())) {
                    isDelete = false;
                    // 업데이트
                    originalQuestion.deleteFromSurvey();
                    questionRepository.save(originalQuestion);

                    Question question = newQuestion.toEntity();
                    survey.addQuestion(question);
                    questionRepository.save(question);
                }
            }

            // 삭제
            if (isDelete) {
                originalQuestion.deleteFromSurvey();
                questionRepository.save(originalQuestion);
            }
        }

        for (QuestionRequest newQuestion: newQuestions) {
            // 생성
            if (newQuestion.getIdx() == null) {
                Question question = newQuestion.toEntity();
                survey.addQuestion(question);
                questionRepository.save(question);
            }
        }

        SurveyCreationResponse response = new SurveyCreationResponse(request.toEntity());
        return new ApiResponse<>(ResponseCode.SUCCESS, response);
    }

}

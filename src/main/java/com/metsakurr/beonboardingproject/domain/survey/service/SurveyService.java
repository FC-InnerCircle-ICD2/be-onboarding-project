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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    public ApiResponse<SurveyCreationResponse> create(SurveyRequest request) {
        Survey survey = Survey.create(request);
        surveyRepository.save(survey);

        SurveyCreationResponse response = new SurveyCreationResponse(survey);
        return new ApiResponse<>(ResponseCode.SUCCESS, response);
    }

    public ApiResponse<SurveyCreationResponse> update(SurveyRequest request) {
        final long surveyIdx = request.getIdx();

        Survey survey = surveyRepository.findById(surveyIdx)
                .orElseThrow(() -> new ServiceException(ResponseCode.NOT_FOUND_SURVEY));

        // 설문조사 이름, 설명 업데이트
        survey.update(request.getName(), request.getDescription());

        List<Question> originalQuestions = survey.getQuestions();
        Map<Long, QuestionRequest> questionRequestMap = request.getQuestions().stream()
                .collect(Collectors.toMap(QuestionRequest::getIdx, question -> question));

        // 삭제 : 업데이트 목록에 원래 질문 항목이 없는 경우
        List<Question> deleteQuestions = deleteQuestions(questionRequestMap, survey);
        deleteQuestions.forEach(question -> {
            survey.deleteQuestion(question);
            questionRepository.delete(question.getIdx());
        });

        // 업데이트 : 기존과 같은 idx
        updateQuestions(originalQuestions, questionRequestMap);

        // 추가 : idx가 null 인 경우
        List<Question> insertQuestions = getInsertQuestions(request.getQuestions(), survey);
        insertQuestions.forEach(survey::addQuestion);

        // 설문조사 저장
        Survey newSurvey = surveyRepository.save(survey);
        SurveyCreationResponse response = new SurveyCreationResponse(newSurvey);
        return new ApiResponse<>(ResponseCode.SUCCESS, response);
    }
    private List<Question> deleteQuestions(Map<Long, QuestionRequest> questionRequestMap, Survey survey) {
         return survey.getQuestions().stream()
                .filter(originalQuestion -> questionRequestMap.get(originalQuestion.getIdx()) == null)
                .toList();
    }

    private void updateQuestions(List<Question> originalQuestions, Map<Long, QuestionRequest> questionRequestMap) {
        originalQuestions.forEach(originalQuestion -> {
            QuestionRequest questionRequest = questionRequestMap.get(originalQuestion.getIdx());
            originalQuestion.update(questionRequest);
        });
    }

    private List<Question> getInsertQuestions(List<QuestionRequest> request, Survey survey) {
        return request.stream()
                .filter(questionRequest -> questionRequest.getIdx() == null)
                .map(Question::create)
                .toList();
    }
}

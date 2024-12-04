package com.onboarding.beonboardingproject.dto;

import com.onboarding.beonboardingproject.domain.response.Answer;
import com.onboarding.beonboardingproject.domain.response.Response;
import com.onboarding.beonboardingproject.domain.survey.Question;
import com.onboarding.beonboardingproject.domain.survey.Survey;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseDto {
    private List<AnswerDto> answers; // 답변 리스트
    private Long surveyId;

    public ResponseDto(List<AnswerDto> answerDtos, Long surveyId) {
        this.answers = answerDtos;
        this.surveyId = surveyId;
    }

    public static ResponseDto of(Response response) {
        List<AnswerDto> answers = new ArrayList<>();

        for(Answer answer : response.getAnswers()) {
            answers.add(AnswerDto.of(answer));
        }
        return new ResponseDto(answers, response.getSurvey().getId());
    }

    public static List<ResponseDto> listOf(List<Response> responseList){
        List<ResponseDto> responseDtos = new ArrayList<>();

        for(Response response : responseList) {
            responseDtos.add(of(response));
        }
        return responseDtos;
    }
}

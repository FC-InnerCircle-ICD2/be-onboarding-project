package com.onboarding.beonboardingproject.dto;

import com.onboarding.beonboardingproject.domain.response.Answer;
import com.onboarding.beonboardingproject.domain.response.Response;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseDto {
    private Long surveyId;
    private List<AnswerDto> answers; // 답변 리스트

    public ResponseDto(Long surveyId, List<AnswerDto> answerDtos) {
        this.surveyId = surveyId;
        this.answers = answerDtos;
    }

    public static ResponseDto of(Response response) {
        List<AnswerDto> answers = new ArrayList<>();

        for(Answer answer : response.getAnswers()) {
            answers.add(AnswerDto.of(answer));
        }
        return new ResponseDto(response.getId(), answers);
    }

    public static List<ResponseDto> listOf(List<Response> responseList){
        List<ResponseDto> responseDtos = new ArrayList<>();

        for(Response response : responseList) {
            responseDtos.add(of(response));
        }
        return responseDtos;
    }
}

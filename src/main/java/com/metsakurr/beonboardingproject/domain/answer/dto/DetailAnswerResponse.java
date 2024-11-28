package com.metsakurr.beonboardingproject.domain.answer.dto;

import com.metsakurr.beonboardingproject.domain.answer.entity.Answer;
import com.metsakurr.beonboardingproject.domain.answer.entity.Response;
import lombok.Getter;

import java.util.List;

@Getter
public class DetailAnswerResponse {
    private long idx; // response idx
    private String name;
    private String description;
    private List<AnswerQuestionResponse> answers;

    @Getter
    public static class AnswerQuestionResponse {
        private long idx;
        private String name;
        private String description;
        private String answer;

        public AnswerQuestionResponse(Answer answer) {
            this.idx = answer.getIdx();
            this.name = answer.getQuestion().getName();
            this.description = answer.getQuestion().getDescription();
            this.answer = answer.getAnswerText();
        }
    }

    public DetailAnswerResponse(Response response) {
        this.idx = response.getIdx();
        this.name = response.getSurvey().getName();
        this.description = response.getSurvey().getDescription();
        this.answers = response.getAnswers().stream()
                .map(AnswerQuestionResponse::new)
                .toList();
    }
}

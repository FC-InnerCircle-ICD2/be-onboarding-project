package com.metsakurr.beonboardingproject.domain.submission.dto;

import com.metsakurr.beonboardingproject.domain.submission.entity.Answer;
import com.metsakurr.beonboardingproject.domain.submission.entity.Submission;
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

    public DetailAnswerResponse(Submission submission) {
        this.idx = submission.getIdx();
        this.name = submission.getSurvey().getName();
        this.description = submission.getSurvey().getDescription();
        this.answers = submission.getAnswers().stream()
                .map(AnswerQuestionResponse::new)
                .toList();
    }
}

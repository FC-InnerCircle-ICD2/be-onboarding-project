package com.metsakurr.beonboardingproject.domain.answer.dto;

import com.metsakurr.beonboardingproject.domain.answer.repository.AnswerRepository;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateAnswerResponse {
    private long idx; // response idx
    private String name;
    private String description;
    private List<AnswerResponse> answers;

    @Getter
    public static class AnswerResponse {
        private long idx;
        private String name;
        private String description;
        private String answer;

        @Builder
        public AnswerResponse(
                long idx,
                String name,
                String description,
                String answer
        ) {
            this.idx = idx;
            this.name = name;
            this.description = description;
            this.answer = answer;
        }
    }

    @Builder
    public CreateAnswerResponse(
            long idx,
            String name,
            String description,
            List<AnswerResponse> answers
    ) {
        this.idx = idx;
        this.name = name;
        this.description = description;
        this.answers = answers;
    }
}

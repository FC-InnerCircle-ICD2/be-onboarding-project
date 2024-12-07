package com.metsakurr.beonboardingproject.domain.submission.dto;

import com.metsakurr.beonboardingproject.domain.submission.entity.Answer;
import com.metsakurr.beonboardingproject.domain.submission.entity.Submission;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class SubmissionCreationResponse {
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

        public static AnswerResponse fromEntity(Answer answer) {
            return AnswerResponse.builder()
                    .idx(answer.getIdx())
                    .name(answer.getQuestion().getName())
                    .description(answer.getQuestion().getDescription())
                    .answer(answer.getAnswerText())
                    .build();
        }
    }

    @Builder
    public SubmissionCreationResponse(
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

    public static SubmissionCreationResponse fromEntity(Submission submission) {
        return SubmissionCreationResponse.builder()
                .idx(submission.getIdx())
                .name(submission.getSurvey().getName())
                .description(submission.getSurvey().getDescription())
                .answers(
                        submission.getAnswers().stream()
                        .map(SubmissionCreationResponse.AnswerResponse::fromEntity)
                        .toList()
                )
                .build();
    }
}

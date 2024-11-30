package com.metsakurr.beonboardingproject.domain.survey.dto;

import com.metsakurr.beonboardingproject.domain.survey.entity.Option;
import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
public class SurveyCreationResponse {
    private long idx;
    private String name;
    private String description;
    private List<QuestionResponse> questions;

    @Getter
    public static class QuestionResponse {
        private long idx;
        private String name;
        private String description;
        private List<OptionResponse> options;

        public QuestionResponse(Question question) {
            this.idx = question.getIdx();
            this.name = question.getName();
            this.description = question.getDescription();
            this.options = Optional.ofNullable(question.getOptions())
                            .orElseGet(Collections::emptyList).stream()
                            .map(OptionResponse::new).toList();
        }

        @Getter
        public static class OptionResponse {
            private long idx;
            private String name;

            public OptionResponse(Option option) {
                this.idx = option.getIdx();
                this.name = option.getName();
            }
        }
    }

    public SurveyCreationResponse(Survey survey) {
        this.idx = survey.getIdx();
        this.name = survey.getName();
        this.description = survey.getDescription();
        this.questions = survey.getQuestions().stream().map(QuestionResponse::new).toList();
    }
}

package com.metsakurr.beonboardingproject.domain.survey.dto;

import com.metsakurr.beonboardingproject.domain.survey.entity.Option;
import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import lombok.Getter;

import java.util.List;

@Getter
public class RegistSurveyResponse {
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

        @Getter
        public static class OptionResponse {
            private String name;

            public OptionResponse(Option option) {
                this.name = option.getName();
            }
        }

        public QuestionResponse(Question question) {
            this.idx = question.getIdx();
            this.name = question.getName();
            this.description = question.getDescription();
            if (this.options != null) {
                this.options = question.getOptions().stream().map(OptionResponse::new).toList();
            }
        }
    }

    public RegistSurveyResponse(Survey survey) {
        this.idx = survey.getIdx();
        this.name = survey.getName();
        this.description = survey.getDescription();
        this.questions = survey.getQuestions().stream().map(QuestionResponse::new).toList();
    }
}

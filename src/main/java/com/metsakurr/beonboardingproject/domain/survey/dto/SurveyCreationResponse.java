package com.metsakurr.beonboardingproject.domain.survey.dto;

import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import lombok.Getter;
import java.util.List;


@Getter
public class SurveyCreationResponse {
    private Long idx;
    private String name;
    private String description;
    private List<QuestionResponse> questions;

    @Getter
    public static class QuestionResponse {
        private long idx;
        private String name;
        private String description;
        private List<String> options;

        public QuestionResponse(Question question) {
            this.idx = question.getIdx();
            this.name = question.getName();
            this.description = question.getDescription();
            this.options = question.getOptions();
        }
    }

    public SurveyCreationResponse(Survey survey) {
        this.idx = survey.getIdx();
        this.name = survey.getName();
        this.description = survey.getDescription();
        this.questions = survey.getQuestions().stream().map(QuestionResponse::new).toList();
    }
}

package com.survey.www.surveys.dto.response;

import com.survey.www.surveys.code.SurveyQuestionType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SurveyQuestionDetailResponse {
    private String surveyNm;
    private String description;
    private Boolean isDeleted;
    private List<SurveyQuestionCommand> surveyQuestions;

    @Getter
    @Builder
    public static class SurveyQuestionCommand {
        private Long id;
        private String questionNm;
        private String description;
        private SurveyQuestionType surveyQuestionType;
        private Boolean isRequired;
        private Boolean isDeleted;
        private List<SurveyQuestionOptionCommand> surveyQuestionOptions;
    }

    @Getter
    @Builder
    public static class SurveyQuestionOptionCommand {
        private Long id;
        private String content;
        private Boolean isDeleted;
    }
}

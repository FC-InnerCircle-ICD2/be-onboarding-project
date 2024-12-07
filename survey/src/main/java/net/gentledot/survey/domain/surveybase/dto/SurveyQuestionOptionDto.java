package net.gentledot.survey.domain.surveybase.dto;

import lombok.Getter;
import lombok.ToString;


@ToString
@Getter
public class SurveyQuestionOptionDto {
    private final String option;

    public SurveyQuestionOptionDto(String option) {
        this.option = option;
    }
}

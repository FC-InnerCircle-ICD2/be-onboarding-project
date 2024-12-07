package net.gentledot.survey.domain.surveybase;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.domain.surveybase.dto.SurveyQuestionOptionDto;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Embeddable
public class SurveyQuestionOption {
    private String optionText;

    public static SurveyQuestionOption from(SurveyQuestionOptionDto surveyQuestionOption) {
        return new SurveyQuestionOption(surveyQuestionOption.getOption());
    }
}
package net.gentledot.survey.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.dto.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.model.enums.AnswerType;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Embeddable
public class SurveyQuestionOption {
    private String optionText;
    private AnswerType answerType;

    public static SurveyQuestionOption of(String option, AnswerType answerType) {
        return new SurveyQuestionOption(option, answerType);
    }

    public static SurveyQuestionOption from(SurveyQuestionOptionRequest surveyQuestionOptionRequest) {
        return SurveyQuestionOption.of(surveyQuestionOptionRequest.getOptionText(), surveyQuestionOptionRequest.getAnswerType());
    }
}
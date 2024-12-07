package net.gentledot.survey.domain.surveybase;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.application.service.in.model.request.SurveyQuestionOptionRequest;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Embeddable
public class SurveyQuestionOption {
    private String optionText;

    public static SurveyQuestionOption from(SurveyQuestionOptionRequest option) {
        // 다뤄질 타입에 따라 추가
        return new SurveyQuestionOption(option.getOption());
    }
}
package net.gentledot.survey.model.entity.surveybase;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.dto.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.model.enums.AnswerType;
import net.gentledot.survey.model.enums.SurveyItemType;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Embeddable
public class SurveyQuestionOption {
    private String optionText;
    private AnswerType answerType;

    public static SurveyQuestionOption of(SurveyQuestionOptionRequest option, SurveyItemType questionType) {
        if (SurveyItemType.SINGLE_SELECT.equals(questionType) || SurveyItemType.MULTI_SELECT.equals(questionType)) {
            return new SurveyQuestionOption(option.getOption(), AnswerType.BOOLEAN);
        }

        // 다뤄질 타입에 따라 추가
        return new SurveyQuestionOption(option.getOption(), AnswerType.TEXT);
    }
}
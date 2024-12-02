package net.gentledot.survey.model.entity.surveyanswer;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.model.entity.common.AnswerConverter;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestionOption;
import net.gentledot.survey.model.enums.AnswerType;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Embeddable
public class SurveyQuestionOptionSnapshot {
    private String optionText;
    private AnswerType answerType;
    @Lob
    @Convert(converter = AnswerConverter.class)
    private Object answer;

    public static SurveyQuestionOptionSnapshot of(SurveyQuestionOption surveyQuestionOption, Object answer) {
        return new SurveyQuestionOptionSnapshot(
                surveyQuestionOption.getOptionText(),
                surveyQuestionOption.getAnswerType(),
                answer);
    }
}
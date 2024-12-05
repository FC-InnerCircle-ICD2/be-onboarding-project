package net.gentledot.survey.model.entity.surveyanswer;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestion;
import net.gentledot.survey.model.enums.AnswerType;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Embeddable
public class SurveyQuestionSnapshot {
    private String itemName;
    private String itemDescription;
    @Enumerated(EnumType.STRING)
    private SurveyItemType itemType;
    private ItemRequired required;
    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    public static SurveyQuestionSnapshot from(SurveyQuestion surveyQuestion) {
        AnswerType targetAnswerType = categorizeFromSurveyQuestion(surveyQuestion.getItemType());
        return new SurveyQuestionSnapshot(
                surveyQuestion.getItemName(),
                surveyQuestion.getItemDescription(),
                surveyQuestion.getItemType(),
                surveyQuestion.getRequired(),
                targetAnswerType
        );
    }

    private static AnswerType categorizeFromSurveyQuestion(SurveyItemType itemType) {
        AnswerType targetAnswerType = AnswerType.TEXT;
        if (SurveyItemType.SINGLE_SELECT.equals(itemType) || SurveyItemType.MULTI_SELECT.equals(itemType)) {
            targetAnswerType = AnswerType.SELECTION;
        }

        return targetAnswerType;
    }
}
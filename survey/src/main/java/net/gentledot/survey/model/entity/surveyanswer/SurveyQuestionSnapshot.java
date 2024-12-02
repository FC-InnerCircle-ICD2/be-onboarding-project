package net.gentledot.survey.model.entity.surveyanswer;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestion;
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
    private SurveyItemType itemType;
    private ItemRequired required;

    public static SurveyQuestionSnapshot from(SurveyQuestion surveyQuestion) {
        return new SurveyQuestionSnapshot(
                surveyQuestion.getItemName(),
                surveyQuestion.getItemDescription(),
                surveyQuestion.getItemType(),
                surveyQuestion.getRequired()
        );
    }
}
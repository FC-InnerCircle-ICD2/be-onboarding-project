package net.gentledot.survey.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class SurveyItem {
    private String itemName;
    private String itemDescription;
    private SurveyItemType itemType;
    private ItemRequired required;
    private String answer;

    public static SurveyItem of(String itemName, String itemDescription, SurveyItemType itemType, ItemRequired required) {
        return new SurveyItem(itemName, itemDescription, itemType, required, null);
    }
}

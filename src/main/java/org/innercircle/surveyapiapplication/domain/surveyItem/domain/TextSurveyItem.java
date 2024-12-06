package org.innercircle.surveyapiapplication.domain.surveyItem.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;

@Getter
public class TextSurveyItem extends SurveyItem {

    public TextSurveyItem(Long id, int version, String name, String description, boolean required, Long surveyId) {
        super(id, version, name, description, required, surveyId);
    }

    public TextSurveyItem(Long id, String name, String description, boolean required) {
        super(id, 1, name, description, required);
    }

    @Override
    public SurveyItemType getType() {
        return SurveyItemType.TEXT;
    }

}

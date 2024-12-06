package org.innercircle.surveyapiapplication.domain.surveyItem.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;

import java.util.List;

@Getter
public class SingleChoiceSurveyItem extends SurveyItem {

    private List<String> options;

    public SingleChoiceSurveyItem(Long id, int version, String name, String description, boolean required, Long surveyId, List<String> options) {
        super(id, version, name, description, required, surveyId);
        this.options = options;
    }

    public SingleChoiceSurveyItem(Long id, String name, String description, boolean required, List<String> options) {
        super(id, 1, name, description, required);
        this.options = options;
    }

    @Override
    public SurveyItemType getType() {
        return SurveyItemType.SINGLE_CHOICE_ANSWER;
    }

}

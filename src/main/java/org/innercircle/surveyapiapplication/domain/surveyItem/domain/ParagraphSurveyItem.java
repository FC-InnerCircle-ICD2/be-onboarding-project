package org.innercircle.surveyapiapplication.domain.surveyItem.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;

@Getter
public class ParagraphSurveyItem extends SurveyItem {

    private SurveySubmission surveySubmission;

    public ParagraphSurveyItem(Long id, int version, String name, String description, boolean required, Long surveyId) {
        super(id, version, name, description, required, surveyId);
    }

    public ParagraphSurveyItem(Long id, String name, String description, boolean required) {
        super(id, 1, name, description, required);
    }

    @Override
    public void answer(SurveySubmission surveySubmission) {
        this.surveySubmission = surveySubmission;
        surveySubmission.setSurveyItemId(this.getId());
    }

    @Override
    public SurveyItemType getType() {
        return SurveyItemType.PARAGRAPH;
    }

}

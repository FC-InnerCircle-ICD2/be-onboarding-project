package org.innercircle.surveyapiapplication.domain.surveySubmission.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

@Getter
public class TextSurveySubmission extends SurveySubmission<String> {

    public TextSurveySubmission(Long id, Long surveyItemId, int surveyItemVersion, String response) {
        super(id, surveyItemId, surveyItemVersion, response);
    }

    public TextSurveySubmission(String response) {
        super(response);
    }

    @Override
    public SurveySubmission<String> setSurveyItemIdAndVersion(SurveyItem surveyItem) {
        if (!surveyItem.getType().equals(SurveyItemType.TEXT)) {
            throw new CustomException(CustomResponseStatus.NOT_MATCH_SURVEY_ITEM_SUBMISSION_TYPE);
        }
        this.surveyItemId = surveyItem.getId();
        this.surveyItemVersion = surveyItem.getVersion();
        return this;
    }

}

package org.innercircle.surveyapiapplication.domain.surveySubmission.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

@Getter
public class ParagraphSurveySubmission extends SurveySubmission<String> {

    public ParagraphSurveySubmission(Long id, Long surveyItemId, int surveyItemVersion, String response) {
        super(id, surveyItemId, surveyItemVersion, response);
    }

    public ParagraphSurveySubmission(String response) {
        super(response);
    }

    @Override
    public SurveySubmission<String> setSurveyItemIdAndVersion(SurveyItem surveyItem) {
        if (!surveyItem.getType().equals(SurveyItemType.PARAGRAPH)) {
            throw new CustomException(CustomResponseStatus.NOT_MATCH_SURVEY_ITEM_SUBMISSION_TYPE);
        }
        this.surveyItemId = surveyItem.getId();
        this.surveyItemVersion = surveyItem.getVersion();
        return this;
    }

}

package org.innercircle.surveyapiapplication.domain.surveySubmission.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SingleChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

@Getter
public class SingleChoiceSurveySubmission extends SurveySubmission<String> {

    public SingleChoiceSurveySubmission(Long id, Long surveyItemId, int surveyItemVersion, String response) {
        super(id, surveyItemId, surveyItemVersion, response);
    }

    public SingleChoiceSurveySubmission(String response) {
        super(response);
    }

    @Override
    public SurveySubmission<String> setSurveyItemIdAndVersion(SurveyItem surveyItem) {
        if (!surveyItem.getType().equals(SurveyItemType.SINGLE_CHOICE_ANSWER)) {
            throw new CustomException(CustomResponseStatus.NOT_MATCH_SURVEY_ITEM_SUBMISSION_TYPE);
        }
        if (!((SingleChoiceSurveyItem) surveyItem).getOptions().contains(this.getResponse())) {
            throw new CustomException(CustomResponseStatus.NOT_FOUND_SURVEY_SUBMISSION_IN_ITEM_OPTION);
        }
        this.surveyItemId = surveyItem.getId();
        this.surveyItemVersion = surveyItem.getVersion();
        return this;
    }

}

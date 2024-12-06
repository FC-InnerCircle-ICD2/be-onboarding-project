package org.innercircle.surveyapiapplication.domain.surveySubmission.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.MultiChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

import java.util.List;

@Getter
public class MultiChoiceSurveySubmission extends SurveySubmission<List<String>> {

    public MultiChoiceSurveySubmission(Long id, Long surveyItemId, int surveyItemVersion, List<String> response) {
        super(id, surveyItemId, surveyItemVersion, response);
    }

    public MultiChoiceSurveySubmission(List<String> response) {
        super(response);
    }

    @Override
    public SurveySubmission<List<String>> setSurveyItemIdAndVersion(SurveyItem surveyItem) {
        if (!surveyItem.getType().equals(SurveyItemType.MULTI_CHOICE_ANSWER)) {
            throw new CustomException(CustomResponseStatus.NOT_MATCH_SURVEY_ITEM_SUBMISSION_TYPE);
        }
        if (!((MultiChoiceSurveyItem) surveyItem).getOptions().containsAll(this.getResponse())) {
            throw new CustomException(CustomResponseStatus.NOT_FOUND_SURVEY_SUBMISSION_IN_ITEM_OPTION);
        }
        this.surveyItemId = surveyItem.getId();
        this.surveyItemVersion = surveyItem.getVersion();
        return this;
    }

}

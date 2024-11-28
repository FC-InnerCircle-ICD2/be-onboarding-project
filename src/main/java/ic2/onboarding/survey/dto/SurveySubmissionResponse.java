package ic2.onboarding.survey.dto;

import ic2.onboarding.survey.entity.SurveySubmissionItem;

import java.util.List;

public record SurveySubmissionResponse(List<SurveyFormSubmissionItem> items)
{

    public static SurveySubmissionResponse fromEntity(List<SurveySubmissionItem> items) {

        List<SurveyFormSubmissionItem> itemList = items.stream()
                .map(SurveyFormSubmissionItem::fromEntity)
                .toList();

        return new SurveySubmissionResponse(itemList);
    }

}

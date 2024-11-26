package ic2.onboarding.survey.dto;

import ic2.onboarding.survey.entity.Survey;

import java.util.List;

public record SurveyUpdateResponse(SurveyForm basic,
                                   List<SurveyFormItem> items) {

    public static SurveyUpdateResponse fromEntity(Survey survey) {

        SurveyForm surveyForm = new SurveyForm(survey.getId(), survey.getName(), survey.getDescription());

        List<SurveyFormItem> formItems = survey.getItems()
                .stream()
                .map(SurveyFormItem::fromEntity)
                .toList();

        return new SurveyUpdateResponse(surveyForm, formItems);
    }

}

package ic2.onboarding.survey.dto;

import jakarta.validation.Valid;

import java.util.List;

public record SurveySubmissionRequest(@Valid List<SurveyFormSubmissionItem> items)
{

}

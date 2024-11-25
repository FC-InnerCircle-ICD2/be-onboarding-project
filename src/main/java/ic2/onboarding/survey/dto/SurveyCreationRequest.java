package ic2.onboarding.survey.dto;

import jakarta.validation.Valid;

import java.util.List;

public record SurveyCreationRequest(@Valid SurveyForm basic,
                                    @Valid List<SurveyFormItem> items) {
}

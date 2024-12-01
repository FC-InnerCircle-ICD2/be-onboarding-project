package ic2.onboarding.survey.dto;

import ic2.onboarding.survey.entity.SurveySubmissionItem;
import jakarta.validation.constraints.NotNull;

public record SurveyFormSubmissionItem(Long id,

                                       @NotNull(message = "{itemId.notNull}")
                                       Long itemId,

                                       String name,
                                       String inputType,

                                       @NotNull(message = "{answer.notNull}")
                                       String answer)
{

    public static SurveyFormSubmissionItem fromEntity(SurveySubmissionItem item) {

        return new SurveyFormSubmissionItem(
                item.getId(),
                item.getSurveyItem().getId(),
                item.getName(),
                item.getInputType().name(),
                item.getAnswer());
    }

}

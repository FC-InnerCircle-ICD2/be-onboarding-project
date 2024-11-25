package ic2.onboarding.survey.dto;

import ic2.onboarding.survey.global.BizConstants;
import jakarta.validation.constraints.Size;

public record SurveyForm(Long id,

                         @Size(min = BizConstants.MIN_NAME_LENGTH,
                                 max = BizConstants.MAX_NAME_LENGTH,
                                 message = "{name.length}")
                         String name,

                         @Size(min = BizConstants.MIN_DESCRIPTION_LENGTH,
                                 max = BizConstants.MAX_DESCRIPTION_LENGTH,
                                 message = "{description.length}")
                         String description
) {
}

package ic2.onboarding.survey.dto;

import ic2.onboarding.survey.global.BizConstants;
import jakarta.validation.constraints.Size;

public record SurveyForm(Long id,

                         @Size(message = "{name.length}",
                                 min = BizConstants.MIN_NAME_LENGTH,
                                 max = BizConstants.MAX_NAME_LENGTH)
                         String name,

                         @Size(message = "{description.length}",
                                 min = BizConstants.MIN_DESCRIPTION_LENGTH,
                                 max = BizConstants.MAX_DESCRIPTION_LENGTH)
                         String description)
{

}

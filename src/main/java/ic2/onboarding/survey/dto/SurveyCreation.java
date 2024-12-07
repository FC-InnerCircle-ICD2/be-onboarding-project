package ic2.onboarding.survey.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public final class SurveyCreation {

    private String uuid;

    @Valid
    @NotNull
    private SurveyInfo info;

}

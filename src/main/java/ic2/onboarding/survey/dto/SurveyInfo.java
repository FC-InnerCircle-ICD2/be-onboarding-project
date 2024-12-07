package ic2.onboarding.survey.dto;

import ic2.onboarding.survey.global.BizConstants;
import ic2.onboarding.survey.global.InputType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public final class SurveyInfo {

    @Valid
    @NotNull
    private Basic basic;

    @Valid
    @Size(message = "{questions.size}",
            min = BizConstants.MIN_QUESTION_SIZE,
            max = BizConstants.MAX_QUESTION_SIZE)
    private List<Question> questions;


    @Getter
    @Setter
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Basic {

        @Size(message = "{name.length}",
                min = BizConstants.MIN_NAME_LENGTH,
                max = BizConstants.MAX_NAME_LENGTH)
        private String name;

        @Size(message = "{description.length}",
                min = BizConstants.MIN_DESCRIPTION_LENGTH,
                max = BizConstants.MAX_DESCRIPTION_LENGTH)
        private String description;

    }

    @Getter
    @Setter
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Question {

        @Size(message = "{name.length}",
                min = BizConstants.MIN_NAME_LENGTH,
                max = BizConstants.MAX_NAME_LENGTH)
        private String name;

        @Size(message = "{description.length}",
                min = BizConstants.MIN_DESCRIPTION_LENGTH,
                max = BizConstants.MAX_DESCRIPTION_LENGTH)
        private String description;

        @NotNull(message = "{inputType.notNull}")
        private InputType inputType;

        @NotNull(message = "{required.notNull}")
        private Boolean required;

        @Size(message = "{options.size}",
                min = BizConstants.MIN_CHOICES_SIZE,
                max = BizConstants.MAX_CHOICES_SIZE)
        private List<String> choiceOptions;

    }

}

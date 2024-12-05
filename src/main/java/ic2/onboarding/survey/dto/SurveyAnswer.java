package ic2.onboarding.survey.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public final class SurveyAnswer {

    private Long submissionId;
    private List<AnswerInfo> answers;

}

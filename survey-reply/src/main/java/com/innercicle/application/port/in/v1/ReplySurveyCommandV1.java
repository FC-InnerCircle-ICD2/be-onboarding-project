package com.innercicle.application.port.in.v1;

import com.innercicle.domain.ReplySurvey;
import com.innercicle.validation.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplySurveyCommandV1 extends SelfValidating<ReplySurveyCommandV1> {

    @NotNull(message = "설문 ID는 필수입니다.")
    private Long surveyId;
    private String name;
    @NotNull(message = "응답자 email은 필수입니다.")
    private String replierEmail;
    private String description;
    private List<ReplySurveyItemCommandV1> items;

    public ReplySurvey mapToDomain() {
        return ReplySurvey.builder()
            .surveyId(surveyId)
            .name(name)
            .replierEmail(replierEmail)
            .description(description)
            .items(items.stream()
                       .map(ReplySurveyItemCommandV1::mapToDomain)
                       .toList())
            .build();
    }

}

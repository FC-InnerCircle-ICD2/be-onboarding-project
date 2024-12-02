package com.innercicle.application.port.in.v1;

import com.innercicle.SelfValidating;
import com.innercicle.domain.v1.Survey;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterSurveyCommandV1 extends SelfValidating<RegisterSurveyCommandV1> {

    @NotNull(message = "설문 명은 필수 입니다.")
    private String name;

    @NotNull(message = "설문 설명은 필수 입니다.")
    private String description;

    @NotNull(message = "설문 항목 목록은 필수 입니다.")
    private List<RegisterSurveyItemCommandV1> items;

    public Survey mapToDomain() {
        return Survey.builder()
            .name(name)
            .description(description)
            .items(items.stream()
                       .map(RegisterSurveyItemCommandV1::mapToDomain)
                       .toList())
            .build();
    }

}

package com.metsakurr.beonboardingproject.domain.survey.dto;

import com.metsakurr.beonboardingproject.domain.survey.entity.Option;
import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
public class OptionRequest {
    private Long idx;

    @NotBlank(message = "설문 받을 항목의 후보 값에는 name이 필요합니다.")
    private String name;

    public Option toEntity() {
        return Option.builder()
                .name(name)
                .build();
    }

    @Builder
    protected OptionRequest(
            String name
    ) {
        this.name = name;
    }
}
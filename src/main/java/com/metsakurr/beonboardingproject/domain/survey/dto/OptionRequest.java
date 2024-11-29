package com.metsakurr.beonboardingproject.domain.survey.dto;

import com.metsakurr.beonboardingproject.domain.survey.entity.Option;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OptionRequest {
    @NotBlank(message = "설문 받을 항목의 후보 값에는 name이 필요합니다.")
    private String name;

    public Option toEntity() {
        return Option.builder().name(name).build();
    }

    @Builder
    public OptionRequest(
            String name
    ) {
        this.name = name;
    }
}
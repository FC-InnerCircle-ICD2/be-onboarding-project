package com.metsakurr.beonboardingproject.domain.survey.dto;

import com.metsakurr.beonboardingproject.domain.survey.entity.Option;
import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OptionRequest {
    private Long idx;

    @NotBlank(message = "설문 받을 항목의 후보 값에는 name이 필요합니다.")
    private String name;

    public Option toEntity(Question question) {
        return Option.builder()
                .question(question)
                .name(name)
                .build();
    }

    @Builder
    public OptionRequest(
            String name
    ) {
        this.name = name;
    }
}
package com.metsakurr.beonboardingproject.domain.survey.dto;

import com.metsakurr.beonboardingproject.domain.survey.entity.Option;
import lombok.Getter;

@Getter
public class OptionRequest {
    private String name;

    public Option toEntity() {
        return Option.builder().name(name).build();
    }
}
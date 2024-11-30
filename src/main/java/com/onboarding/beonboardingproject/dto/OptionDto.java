package com.onboarding.beonboardingproject.dto;

import com.onboarding.beonboardingproject.domain.response.Answer;
import com.onboarding.beonboardingproject.domain.survey.Option;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class OptionDto {
    private Long optionId;
    private String optionValue;

    public OptionDto(Long optionId, String optionValue) {
        this.optionId = optionId;
        this.optionValue = optionValue;
    }

    public static OptionDto of(Option option) {
        return new OptionDto(option.getId(), option.getOptionValue());
    }
}

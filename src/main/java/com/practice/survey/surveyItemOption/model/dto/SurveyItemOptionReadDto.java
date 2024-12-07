package com.practice.survey.surveyItemOption.model.dto;

import com.practice.survey.surveyItemOption.model.entity.SurveyItemOption;
import lombok.*;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyItemOptionReadDto {

    private String optionText;

    public static SurveyItemOptionReadDto fromEntity(SurveyItemOption surveyItemOption) {
        return SurveyItemOptionReadDto.builder()
                .optionText(surveyItemOption.getOptionText())
                .build();
    }
}

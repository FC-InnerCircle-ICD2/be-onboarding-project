package com.practice.survey.surveymngt.model.dto;

import com.practice.survey.surveyItem.model.dto.SurveyItemSaveRequestDto;
import com.practice.survey.surveymngt.model.entity.Survey;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyRequestDto {

    @NotEmpty(message = "설문조사 이름은 필수 입력 값입니다.")
    private String name;  // 설문조사 이름

    private String description;  // 설문조사 설명

    @NotNull(message = "설문조사 항목은 필수 입력 값입니다.")
    @Size(min = 1, max = 10, message = "설문조사는 1에서 10개까지의 항목만 가질 수 있습니다.")
    private List<SurveyItemSaveRequestDto> surveyItems;  // 설문조사 항목 리스트

    public Survey toEntity() {
        return Survey.builder()
                .name(name)
                .description(description)
                .build();
    }
}

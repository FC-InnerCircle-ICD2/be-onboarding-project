package com.practice.survey.surveyItem.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.survey.surveyItem.model.entity.SurveyItem;
import com.practice.survey.surveyItem.model.enums.InputType;
import com.practice.survey.surveyItemOption.model.dto.SurveyItemOptionSaveRequestDto;
import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyItemSaveRequestDto {

    @NotEmpty
    private String name;  // 항목 이름

    private String description;  // 항목 설명

    @NotNull
    private InputType inputType;  // 입력 형태 (SHORT_TEXT, LONG_TEXT, SINGLE_CHOICE, MULTIPLE_CHOICE 등)

    @JsonProperty("required")
    private boolean required;  // 필수 여부

    private List<SurveyItemOptionSaveRequestDto> options;  // 항목의 선택지 리스트 (옵션이 있는 경우)

    public SurveyItem toEntity(SurveyVersion version, int itemNumber) {
        return SurveyItem.builder()
                .name(name)
                .description(description)
                .inputType(inputType)
                .required(required)
                .version(version)
                .itemNumber(itemNumber)
                .build();
    }
}

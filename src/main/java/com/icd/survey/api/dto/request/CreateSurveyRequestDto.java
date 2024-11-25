package com.icd.survey.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateSurveyRequestDto {
    @NotNull(message = "설문조사 정보를 확인하세요.")
    private SurveyRequest surveyDto;
}

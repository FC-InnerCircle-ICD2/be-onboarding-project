package com.innercircle.surveryproject.modules.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 설문조사 생성 Dto
 */
@Data
@NoArgsConstructor
public class SurveyCreateDto {

    /**
     * 이름
     */
    @NotNull
    private String name;

    /**
     * 설명
     */
    @NotNull
    private String description;

    /**
     * 설문 조사 유형
     */
    @NotNull
    private List<SurveyItemDto> surveyItemDtoList = new ArrayList<>();

}

package com.innercircle.surveryproject.modules.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 설문조사 수정 dto
 */
@Data
public class SurveyUpdateDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private List<SurveyItemDto> surveyItemDtoList = new ArrayList<>();

}

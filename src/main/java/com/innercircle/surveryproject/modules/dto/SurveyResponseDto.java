package com.innercircle.surveryproject.modules.dto;

import lombok.Data;

import java.util.List;

/**
 * 설문조사 응답 dto
 */
@Data
public class SurveyResponseDto {

    /**
     * 설문조사 id
     */
    private Long surveyId;

    /**
     * 설문조사 응답 항목
     */
    private List<SurveyItemResponseDto> surveyItemResponseDtoList;

}

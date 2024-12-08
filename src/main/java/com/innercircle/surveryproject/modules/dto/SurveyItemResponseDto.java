package com.innercircle.surveryproject.modules.dto;

import lombok.Data;

import java.util.List;

/**
 * 설문조사 항목 응답
 */
@Data
public class SurveyItemResponseDto {

    /**
     * 설문조사 항목 아이디
     */
    private Long surveyItemId;

    /**
     * 항목 별 응답 결과
     */
    private List<String> answer;

}
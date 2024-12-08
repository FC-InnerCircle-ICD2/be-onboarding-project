package com.innercircle.surveryproject.modules.dto;

import lombok.Data;

import java.util.List;

/**
 * 설문조사 응답 제출 dto
 */
@Data
public class SurveyAnswerCreateDto {

    /**
     * 사용자이름
     */
    private String username;

    /**
     * 핸드폰 번호
     */
    private Long phoneNumber;

    /**
     * 설문조사 id
     */
    private Long surveyId;

    /**
     * 설문조사 응답 내용
     */
    private List<SurveyItemResponseDto> surveyItemResponseDtoList;

}

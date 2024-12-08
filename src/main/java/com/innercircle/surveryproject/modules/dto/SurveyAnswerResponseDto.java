package com.innercircle.surveryproject.modules.dto;

import lombok.Data;

@Data
public class SurveyAnswerResponseDto {

    /**
     * 설문조사 식별자
     */
    private Long surveyId;

    /**
     * 설문조사 항목 식별자
     */
    private Long surveyItemId;

    /**
     * 핸드폰 번호
     */
    private Long phoneNumber;

    /**
     * 설문조사 항목 응답
     */
    private String surveyItemAnswer;

    public SurveyAnswerResponseDto(Long surveyId, Long phoneNumber, Long surveyItemId, String surveyItemAnswer) {
        this.surveyId = surveyId;
        this.phoneNumber = phoneNumber;
        this.surveyItemId = surveyItemId;
        this.surveyItemAnswer = surveyItemAnswer;
    }

    public static SurveyAnswerResponseDto of(Long surveyId, Long phoneNumber, Long surveyItemId, String surveyItemAnswer) {
        return new SurveyAnswerResponseDto(surveyId, phoneNumber, surveyItemId, surveyItemAnswer);
    }

}

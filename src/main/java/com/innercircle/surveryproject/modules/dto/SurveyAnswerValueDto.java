package com.innercircle.surveryproject.modules.dto;

import com.innercircle.surveryproject.modules.entity.SurveyAnswerMapValue;
import lombok.Data;

import java.util.List;

/**
 * 설문조사 항목 응답
 */
@Data
public class SurveyAnswerValueDto {

    /**
     * 설문조사 항목 식별자
     */
    private Long surveyItemId;

    /**
     * 응답
     */
    private List<String> response;

    public static List<SurveyAnswerValueDto> from(List<SurveyAnswerMapValue> surveyAnswerDetails) {
        return surveyAnswerDetails.stream().map(surveyAnswerMapValue -> {
            SurveyAnswerValueDto surveyAnswerValueDto = new SurveyAnswerValueDto();
            surveyAnswerValueDto.setSurveyItemId(surveyAnswerMapValue.getSurveyItemId());
            surveyAnswerValueDto.setResponse(surveyAnswerMapValue.getResponses());
            return surveyAnswerValueDto;
        }).toList();
    }
}

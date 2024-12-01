package com.innercircle.surveryproject.modules.dto;

import com.innercircle.surveryproject.modules.entity.SurveyAnswer;
import lombok.Data;

import java.util.Map;

/**
 * 설문조사 응답 결과 dto
 */
@Data
public class SurveyAnswerDto {

    /**
     * 휴대폰 번호
     */
    private Long phoneNumber;

    /**
     * 이름
     */
    private String username;

    /**
     * 설문조사 응답 결과
     */
    private Map<Long, String> surveyAnswerMap;

    public SurveyAnswerDto(SurveyAnswer surveyAnswer) {
        this.phoneNumber = surveyAnswer.getPhoneNumber();
        this.username = surveyAnswer.getUsername();
        this.surveyAnswerMap = surveyAnswer.getSurveyAnswerMap();
    }

    public static SurveyAnswerDto from(SurveyAnswer surveyAnswer) {
        return new SurveyAnswerDto(surveyAnswer);
    }

}

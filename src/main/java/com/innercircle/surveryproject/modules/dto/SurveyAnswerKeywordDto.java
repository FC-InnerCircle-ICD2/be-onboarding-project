package com.innercircle.surveryproject.modules.dto;

import com.innercircle.surveryproject.modules.entity.SurveyItem;
import lombok.Data;

import java.util.List;

/**
 * 설문조사 응답 키워드
 * 설문조사 응답 상세 조회를 위한 키워드 조회
 */
@Data
public class SurveyAnswerKeywordDto {

    /**
     * 설문조사 식별자
     */
    private Long surveyId;

    /**
     * 설문조사 항목 식별자
     */
    private Long surveyItemId;

    /**
     * 설문조사 항목
     */
    private String questionName;

    /**
     * 설문조사 항목
     */
    private List<String> questionAnswerList;

    public SurveyAnswerKeywordDto(SurveyItem surveyItem) {
        this.surveyId = surveyItem.getSurveyId();
        this.surveyItemId = surveyItem.getId();
        this.questionName = surveyItem.getName();
        this.questionAnswerList = surveyItem.getItemContentList();
    }

    public static SurveyAnswerKeywordDto from(SurveyItem surveyItem) {
        return new SurveyAnswerKeywordDto(surveyItem);
    }

}

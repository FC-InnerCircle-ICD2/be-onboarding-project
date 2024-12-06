package com.innercircle.surveryproject.modules.dto;

import com.innercircle.surveryproject.modules.entity.Survey;
import com.innercircle.surveryproject.modules.entity.SurveyItem;
import lombok.Data;

import java.util.List;

/**
 * 설문조사 dto
 */
@Data
public class SurveyDto {

    /**
     * 설문조사 아이디
     */
    private Long id;
    /**
     * 설문조사 이름
     */
    private String name;

    /**
     * 설문조사 설명
     */
    private String description;

    /**
     * 설문조사 항목
     */
    private List<SurveyItemDto> surveyItemDtoList;

    public static SurveyDto from(Survey survey) {
        return new SurveyDto(survey);
    }

    private SurveyDto(Survey survey) {
        this.id = survey.getId();
        this.name = survey.getName();
        this.description = survey.getDescription();
        this.surveyItemDtoList = SurveyItem.convert(survey.getSurveyItemList());
    }

}

package com.innercircle.surveryproject.modules.dto;

import com.innercircle.surveryproject.modules.entity.SurveyItem;
import com.innercircle.surveryproject.modules.enums.ItemType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 설문 받은 항목 dto
 */
@Data
@NoArgsConstructor
public class SurveyItemDto {

    /**
     * 이름
     */
    private String name;

    /**
     * 설명
     */
    private String description;

    /**
     * 항목 입력 형태
     */
    private ItemType itemType;

    /**
     * 필수여부
     */
    private Boolean required;

    /**
     * 활성화 여부
     * 설문조사 수정 시 기존에 생성한 데이터를 삭제한 경우
     * FALSE 처리
     */
    private Boolean active;

    /**
     * 설문조사 항목 별 콘텐츠
     */
    private List<String> itemContentList;

    public SurveyItemDto(SurveyItem surveyItem) {
        this.name = surveyItem.getName();
        this.description = surveyItem.getDescription();
        this.itemType = surveyItem.getItemType();
        this.required = surveyItem.getRequired();
        this.active = surveyItem.getActive();
        this.itemContentList = surveyItem.getItemContentList();
    }

    public static SurveyItemDto toDto(SurveyItem surveyItem) {
        return new SurveyItemDto(surveyItem);
    }

}
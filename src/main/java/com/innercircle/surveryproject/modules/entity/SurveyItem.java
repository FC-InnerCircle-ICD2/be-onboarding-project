package com.innercircle.surveryproject.modules.entity;

import com.innercircle.surveryproject.modules.dto.SurveyItemDto;
import com.innercircle.surveryproject.modules.enums.ItemType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 설문조사 항목 엔티티
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyItem {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    /**
     * 이름
     */
    @Column(nullable = false)
    private String name;

    /**
     * 설명
     */
    @Column(nullable = false)
    private String description;

    /**
     * 설문조사 유형
     */
    private ItemType itemType;

    /**
     * 필수여부
     */
    private Boolean required;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    public SurveyItem(SurveyItemDto surveyItemDto) {
        this.name = surveyItemDto.getName();
        this.description = surveyItemDto.getDescription();
        this.itemType = surveyItemDto.getItemType();
        this.required = surveyItemDto.getRequired();
    }

    public static SurveyItem from(SurveyItemDto surveyItemDto) {
        return new SurveyItem(surveyItemDto);
    }

    public static List<SurveyItemDto> convertToDto(List<SurveyItem> surveyItemList) {
        return surveyItemList.stream().map(SurveyItemDto::toDto).toList();
    }

}

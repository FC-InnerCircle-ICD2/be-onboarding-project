package com.innercircle.surveryproject.modules.entity;

import com.innercircle.surveryproject.modules.dto.SurveyItemDto;
import com.innercircle.surveryproject.modules.enums.ItemType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 설문조사 항목 엔티티
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    /**
     * 필수여부
     */
    private Boolean required;

    /**
     * 활성화여부
     */
    private Boolean active = Boolean.TRUE;

    @Setter
    @ManyToOne()
    @JoinColumn(name = "survey_id")
    private Survey survey;

    /**
     * 항목 별 입력 내용
     */
    @ElementCollection
    private List<String> itemContentList;

    public SurveyItem(SurveyItemDto surveyItemDto) {
        this.name = surveyItemDto.getName();
        this.description = surveyItemDto.getDescription();
        this.itemType = surveyItemDto.getItemType();
        this.required = surveyItemDto.getRequired();
        this.itemContentList = surveyItemDto.getItemContentList();
        this.active = surveyItemDto.getActive();
    }

    public static SurveyItem from(SurveyItemDto surveyItemDto) {
        return new SurveyItem(surveyItemDto);
    }

    public static List<SurveyItemDto> convertToDto(List<SurveyItem> surveyItemList) {
        return surveyItemList.stream().map(SurveyItemDto::toDto).toList();
    }

    public static List<SurveyItemDto> convert(List<SurveyItem> surveyItemList) {
        return surveyItemList.stream().filter(SurveyItem::getActive).map(SurveyItemDto::toDto).toList();
    }

    public static List<SurveyItem> convertToEntity(@NotNull List<SurveyItemDto> surveyItemDtoList) {
        return surveyItemDtoList.stream().map(SurveyItem::from).toList();
    }

    public Long getSurveyId() {
        return this.survey.getId();
    }

}

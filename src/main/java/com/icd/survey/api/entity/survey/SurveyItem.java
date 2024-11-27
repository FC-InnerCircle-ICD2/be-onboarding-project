package com.icd.survey.api.entity.survey;

import com.icd.survey.api.entity.base.BaseEntity;
import com.icd.survey.api.entity.dto.SurveyItemDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "survey_item")
public class SurveyItem extends BaseEntity {
    @Id
    @Column(name = "item_seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemSeq;

    @Column(name = "item_name", length = 255, nullable = false)
    private String itemName;

    @Column(name = "item_description", length = 1000, nullable = false)
    private String itemDescription;

    @Column(name = "response_type", nullable = false, columnDefinition = "TINYINT")
    private Integer itemResponseType;

    @Column(name = "is_essential", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private Boolean isEssential = Boolean.FALSE;

    @Column(name = "survey_Seq")
    @Setter
    private Long surveySeq;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_seq")
    private List<ItemResponseOption> responseOptionList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_seq")
    private List<ItemResponse> responseList;

    public List<ItemResponseOption> createResponseOptionList() {
        responseOptionList = new ArrayList<>();
        return responseOptionList;
    }


    public List<ItemResponse> createResponseList() {
        responseList = new ArrayList<>();
        return responseList;
    }

    public void saveResponseOptionList(List<ItemResponseOption> request) {
        responseOptionList = request;
    }

    public void saveResponseList(List<ItemResponse> request) {
        responseList = request;
    }

    public static SurveyItem createSurveyItemRequest(SurveyItemDto dto) {
        SurveyItem surveyItem = new SurveyItem();
        surveyItem.itemName = dto.getItemName();
        surveyItem.itemDescription = dto.getItemDescription();
        surveyItem.itemResponseType = dto.getItemResponseType();
        surveyItem.isEssential = dto.getIsEssential();
        return surveyItem;
    }

    public SurveyItemDto of() {
        return SurveyItemDto
                .builder()
                .itemSeq(this.itemSeq)
                .itemName(this.itemName)
                .itemDescription(this.itemDescription)
                .isEssential(this.isEssential)
                .itemResponseType(this.itemResponseType)
                .build();
    }


}

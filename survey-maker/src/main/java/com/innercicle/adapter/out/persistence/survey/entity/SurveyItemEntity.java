package com.innercicle.adapter.out.persistence.survey.entity;

import com.innercicle.domain.v1.InputType;
import com.innercicle.domain.v1.SurveyItem;
import com.innercicle.entity.UpdatedEntity;
import com.innercicle.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "survey_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyItemEntity extends UpdatedEntity {

    @Id
    @IdGenerator
    @Column(name = "survey_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private SurveyEntity survey;

    /**
     * 항목
     */
    private String item;

    /**
     * 설명
     */
    private String description;

    /**
     * 입력 형태
     */
    @Enumerated(EnumType.STRING)
    private InputType inputType;

    /**
     * 필수 여부
     */
    private boolean required;

    /**
     * 선택지 목록
     */
    @ElementCollection
    @CollectionTable(name = "survey_item_option", joinColumns = @JoinColumn(name = "survey_item_id"))
    @Column(name = "options")
    private List<String> options;

    public static SurveyItemEntity from(SurveyItem surveyItem) {
        SurveyItemEntity entity = new SurveyItemEntity();
        entity.item = surveyItem.item();
        entity.description = surveyItem.description();
        entity.inputType = surveyItem.inputType();
        entity.required = surveyItem.required();
        entity.options = surveyItem.options();
        return entity;
    }

    public SurveyItem mapToDomain() {
        return SurveyItem.builder()
            .id(this.id)
            .item(this.item)
            .description(this.description)
            .inputType(this.inputType)
            .required(this.required)
            .options(this.options)
            .build();
    }

}

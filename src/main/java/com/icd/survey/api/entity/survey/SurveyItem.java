package com.icd.survey.api.entity.survey;

import com.icd.survey.api.dto.survey.request.SurveyItemRequest;
import com.icd.survey.api.entity.base.BaseEntity;
import com.icd.survey.api.entity.dto.SurveyItemDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "survey_item")
@ToString
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
    @Builder.Default
    private Boolean isEssential = Boolean.FALSE;

    @Setter
    @ManyToOne
    @JoinColumn(name = "survey_seq", nullable = false)
    private Survey survey;
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

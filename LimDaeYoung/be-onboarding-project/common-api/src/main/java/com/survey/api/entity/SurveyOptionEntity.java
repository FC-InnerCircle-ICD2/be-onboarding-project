package com.survey.api.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="surveyOption")
public class SurveyOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String optionName;
    private int optionOrder;
    private String useYn;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime regDtm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private SurveyItemEntity surveyItem; // 설문 항목 참조

    public SurveyOptionEntity(String optionName, int optionOrder, String useYn, SurveyItemEntity surveyItem) {
        this.optionName = optionName;
        this.optionOrder = optionOrder;
        this.useYn = useYn;
        this.surveyItem = surveyItem;
    }

    public SurveyOptionEntity(Long id,String optionName, int optionOrder, String useYn, SurveyItemEntity surveyItem) {
        this.id = id;
        this.optionName = optionName;
        this.optionOrder = optionOrder;
        this.useYn = useYn;
        this.surveyItem = surveyItem;
    }

    public SurveyOptionEntity(Long id) {
        this.id = id;
    }
}

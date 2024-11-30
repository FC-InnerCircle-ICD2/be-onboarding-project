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
@Entity(name="surveyResponseOption")
public class SurveyResponseOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime regDtm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "optionId")
    private SurveyOptionEntity surveyOtion; // 설문 항목 참조

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responseItemId")
    private SurveyResponseItemEntity responseItem; // 설문 항목 참조

    public SurveyResponseOptionEntity(SurveyResponseItemEntity responseItem, SurveyOptionEntity surveyOtion) {
        this.regDtm = regDtm;
        this.responseItem = responseItem;
        this.surveyOtion = surveyOtion;

    }
}

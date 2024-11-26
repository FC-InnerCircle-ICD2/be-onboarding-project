package com.survey.api.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="surveyResponseOption")
public class SurveyResponseOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String regDtm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "optionId")
    private SurveyOptionEntity surveyOtion; // 설문 항목 참조

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responseItemId")
    private SurveyResponseItemEntity responseItem; // 설문 항목 참조

    public SurveyResponseOptionEntity(String regDtm, SurveyEntity surveyItem) {
        this.regDtm = regDtm;
    }
}

package com.survey.api.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="surveyResponse")
public class SurveyResponseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String regDtm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surveyId")
    private SurveyEntity survey; // 설문 항목 참조

    @OneToOne(mappedBy = "response", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SurveyResponseItemEntity responseItems; // 설문 항목 리스트목 리스트

    public SurveyResponseEntity(String regDtm, SurveyEntity survey) {
        this.regDtm = regDtm;
        this.survey = survey;
    }
}

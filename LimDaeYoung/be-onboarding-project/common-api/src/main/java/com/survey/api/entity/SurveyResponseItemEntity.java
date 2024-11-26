package com.survey.api.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="surveyResponseItem")
public class SurveyResponseItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String regDtm;
    private String answerText; // 단답형, 장문형의 답변

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responseId")
    private SurveyResponseEntity response; //

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private SurveyItemEntity surveyItem; //

    @OneToOne(mappedBy = "responseItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SurveyResponseOptionEntity reponseOption; //

    public SurveyResponseItemEntity(String regDtm, SurveyResponseEntity response, SurveyItemEntity surveyItem) {
        this.regDtm = regDtm;
        this.response = response;
        this.surveyItem = surveyItem;
    }
}

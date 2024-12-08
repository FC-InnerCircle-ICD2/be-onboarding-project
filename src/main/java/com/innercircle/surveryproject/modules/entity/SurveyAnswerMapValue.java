package com.innercircle.surveryproject.modules.entity;

import com.innercircle.surveryproject.modules.enums.ItemType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "survey_answer_map_value")
public class SurveyAnswerMapValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 설문조사 항목 식별자
     */
    private Long surveyItemId;

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

    @Setter
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "survey_answer_survey_id", referencedColumnName = "survey_id"),
        @JoinColumn(name = "SURVEY_ANSWER_PHONE_NUMBER", referencedColumnName = "phone_number")
    })
    private SurveyAnswer surveyAnswer;

    @ElementCollection
    private List<String> responses;


    public SurveyAnswerMapValue(SurveyItem surveyItem, Long surveyItemId, List<String> answer) {
        this.surveyItemId = surveyItemId;
        this.name = surveyItem.getName();
        this.description = surveyItem.getDescription();
        this.itemType = surveyItem.getItemType();
        this.required = surveyItem.getRequired();
        this.responses = answer;
    }

    public static SurveyAnswerMapValue from(SurveyItem surveyItem, Long surveyItemId, List<String> answer) {
        return new SurveyAnswerMapValue(surveyItem, surveyItemId, answer);
    }

}
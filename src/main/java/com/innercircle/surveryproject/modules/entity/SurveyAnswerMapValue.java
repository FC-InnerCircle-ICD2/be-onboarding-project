package com.innercircle.surveryproject.modules.entity;

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

    @Setter
    @ManyToOne()
    @JoinColumns({
        @JoinColumn(name = "survey_answer_survey_id", referencedColumnName = "survey_id"), // SurveyAnswer의 실제 컬럼명 확인
        @JoinColumn(name = "SURVEY_ANSWER_PHONE_NUMBER", referencedColumnName = "phone_number") // SurveyAnswer의 실제 컬럼명 확인
    })
    private SurveyAnswer surveyAnswer;

    @ElementCollection
    private List<String> responses;

    public SurveyAnswerMapValue(Long surveyItemId, List<String> answer) {
        this.surveyItemId = surveyItemId;
        this.responses = answer;
    }

    public static SurveyAnswerMapValue from(Long surveyItemId, List<String> answer) {
        return new SurveyAnswerMapValue(surveyItemId, answer);
    }

}
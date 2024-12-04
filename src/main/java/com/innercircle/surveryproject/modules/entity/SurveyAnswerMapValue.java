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

    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    /**
     *
     */
    private Long surveyItemId;

    @Setter @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "SURVEY_ANSWER_SURVEYID", referencedColumnName = "SURVEYID"),
        @JoinColumn(name = "SURVEY_ANSWER_PHONENUMBER", referencedColumnName = "PHONENUMBER"),
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
package com.innercircle.surveryproject.modules.entity;

import com.innercircle.surveryproject.modules.dto.SurveyAnswerCreateDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * 설문조사 응답
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyAnswer extends BaseEntity {

    /**
     * 핸드폰 번호 (국가번호+전화번호)
     */
    @Id
    @Column(nullable = false)
    private Long phoneNumber;

    /**
     * 사용자 이름
     */
    private String username;

    /**
     * 설문조사 응답 결과
     */
    @ElementCollection
    private Map<Long, String> surveyAnswerMap;

    @Setter
    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    public SurveyAnswer(SurveyAnswerCreateDto surveyCreateDto, Map<Long, String> surveyAnswerMap) {
        this.phoneNumber = surveyCreateDto.getPhoneNumber();
        this.username = surveyCreateDto.getUsername();
        this.surveyAnswerMap = surveyAnswerMap;
    }

    public static SurveyAnswer from(SurveyAnswerCreateDto surveyCreateDto, Map<Long, String> surveyAnswerMap) {
        return new SurveyAnswer(surveyCreateDto, surveyAnswerMap);
    }

}

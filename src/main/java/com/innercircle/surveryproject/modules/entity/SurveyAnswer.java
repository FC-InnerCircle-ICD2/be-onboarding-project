package com.innercircle.surveryproject.modules.entity;

import com.innercircle.surveryproject.modules.dto.SurveyAnswerCreateDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 설문조사 응답
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyAnswer extends BaseEntity {

    /**
     * 설문조사 ID
     */
    @EmbeddedId
    private SurveyAnswerId surveyAnswerId;

    /**
     * 사용자 이름
     */
    private String username;

    /**
     * 설문조사 이름
     */
    private String name;

    /**
     * 설문조사 설명
     */
    private String description;

    /**
     * 설문조사 응답 결과
     */
    @Setter
    @OneToMany(mappedBy = "surveyAnswer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyAnswerMapValue> surveyAnswerDetails = new ArrayList<>();

    public SurveyAnswer(Survey survey, SurveyAnswerCreateDto surveyCreateDto) {
        this.surveyAnswerId = SurveyAnswerId.of(surveyCreateDto.getSurveyId(), surveyCreateDto.getPhoneNumber());
        this.username = surveyCreateDto.getUsername();
        this.name = survey.getName();
        this.description = survey.getDescription();
    }

    public static SurveyAnswer from(Survey survey, SurveyAnswerCreateDto surveyCreateDto) {
        return new SurveyAnswer(survey,surveyCreateDto);
    }

    public Long getSurveyId() {
        return this.surveyAnswerId.getSurveyId();
    }

    public Long getPhoneNumber() {
        return this.surveyAnswerId.getPhoneNumber();
    }

}

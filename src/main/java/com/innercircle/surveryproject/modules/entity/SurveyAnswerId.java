package com.innercircle.surveryproject.modules.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyAnswerId implements Serializable {

    @Column(name = "survey_id")
    private Long surveyId;
    @Column(name = "phone_number")
    private Long phoneNumber;

    public SurveyAnswerId(Long surveyId, Long phoneNumber) {
        this.surveyId = surveyId;
        this.phoneNumber = phoneNumber;
    }

    public static SurveyAnswerId of(Long surveyId, Long phoneNumber) {
        return new SurveyAnswerId(surveyId, phoneNumber);
    }

}
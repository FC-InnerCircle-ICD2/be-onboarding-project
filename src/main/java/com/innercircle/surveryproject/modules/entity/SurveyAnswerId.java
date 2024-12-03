package com.innercircle.surveryproject.modules.entity;

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

    private Long surveyId;
    private Long phoneNumber;

    public SurveyAnswerId(Long surveyId, Long phoneNumber) {
        this.surveyId = surveyId;
        this.phoneNumber = phoneNumber;
    }

    public static SurveyAnswerId of(Long surveyId, Long phoneNumber) {
        return new SurveyAnswerId(surveyId, phoneNumber);
    }

}
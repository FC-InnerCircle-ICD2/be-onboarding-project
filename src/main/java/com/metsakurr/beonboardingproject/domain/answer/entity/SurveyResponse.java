package com.metsakurr.beonboardingproject.domain.answer.entity;

import com.metsakurr.beonboardingproject.common.entity.BaseEntity;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "response")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyResponse extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_idx", referencedColumnName = "idx", nullable = false)
    private Survey survey;
}

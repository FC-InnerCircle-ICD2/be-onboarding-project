package com.metsakurr.beonboardingproject.domain.answer.entity;

import com.metsakurr.beonboardingproject.common.entity.BaseEntity;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "response")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyResponse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_idx", referencedColumnName = "idx", nullable = false)
    private Survey survey;
}

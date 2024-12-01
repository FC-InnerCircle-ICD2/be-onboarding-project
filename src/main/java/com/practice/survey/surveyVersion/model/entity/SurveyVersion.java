package com.practice.survey.surveyVersion.model.entity;

import com.practice.survey.common.model.entity.BaseTime;
import com.practice.survey.surveymngt.model.entity.Survey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class SurveyVersion extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long versionId;

    @Column(nullable = false)
    private int versionNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;


}

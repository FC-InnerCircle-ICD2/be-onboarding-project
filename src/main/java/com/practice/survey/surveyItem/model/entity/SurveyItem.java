package com.practice.survey.surveyItem.model.entity;

import com.practice.survey.common.model.entity.BaseTime;
import com.practice.survey.surveyItemOption.model.dto.SurveyItemOptionDto;
import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import com.practice.survey.surveyItem.model.enums.InputType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SurveyItem extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private int itemNumber;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "input_type",nullable = false)
    private InputType inputType;

    @Column(nullable = false)
    private boolean required;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "version_id", nullable = false)
    private SurveyVersion version;

}

package com.practice.survey.surveyItemOption.model.entity;

import com.practice.survey.common.model.entity.BaseTime;
import com.practice.survey.surveyItem.model.entity.SurveyItem;
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
public class SurveyItemOption extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    @Column(nullable = false)
    private int optionNumber;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String optionText;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", nullable = false)
    private SurveyItem item;
}

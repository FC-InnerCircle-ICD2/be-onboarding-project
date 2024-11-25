package com.icd.survey.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "item_response_option")
public class ItemResponseOption {
    @Id
    @Column(name = "option_seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionSeq;
    @Column(name = "option", nullable = false)
    private String option;

    @ManyToOne
    @JoinColumn(name = "item_seq")
    private SurveyItem surveyItem;
}

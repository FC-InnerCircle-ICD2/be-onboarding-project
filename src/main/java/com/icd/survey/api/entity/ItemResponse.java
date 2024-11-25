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
@Table(name = "item_response")
public class ItemResponse {
    @Id
    @Column(name = "response_seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseSeq;
    @Column(name = "response", nullable = false)
    private String response;

    @ManyToOne
    @JoinColumn(name = "item_seq")
    private SurveyItem surveyItem;
}

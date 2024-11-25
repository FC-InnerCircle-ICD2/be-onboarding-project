package com.icd.survey.api.entity;

import com.icd.survey.api.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "survey")
public class Survey extends BaseEntity {
    @Id
    @Column(name = "survey_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveySeq;

    @Column(name = "survey_name", length = 255, nullable = false)
    private String surveyName;

    @Column(name = "survey_description", length = 1000, nullable = false)
    private String surveyDescription;

    @Column(name = "ip_address", length = 255, nullable = false)
    private String ipAddress;


    @OneToMany(mappedBy = "survey")
    private List<SurveyItem> surbeyItemList = new ArrayList<>();
}

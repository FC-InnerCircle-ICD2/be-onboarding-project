package com.survey.api.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="surveyResponse")
public class SurveyResponseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime regDtm;

    private long surveyId; // 설문 항목 참조

    private String snapShotName;
    private String snapShotDescription;

    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SurveyResponseItemEntity> responseItems; // 설문 항목 리스트

    public SurveyResponseEntity(long surveyId, String snapShotName, String snapShotDescription) {
        this.surveyId = surveyId;
        this.snapShotName = snapShotName;
        this.snapShotDescription = snapShotDescription;
    }
}

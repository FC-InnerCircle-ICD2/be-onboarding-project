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
@Entity(name="surveyItem")
public class SurveyItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private String description;
    private String itemType;
    private boolean required;
    private String useYn;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime regDtm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surveyId")
    private SurveyEntity survey; // 설문 ID 참조

    @OneToMany(mappedBy = "surveyItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<SurveyOptionEntity> options; // 선택 항목 리스트

    public SurveyItemEntity(Long id) {
        this.id = id;
    }

    public SurveyItemEntity(String itemName, String description, String itemType, boolean required, String useYn, SurveyEntity survey) {
        this.itemName = itemName;
        this.description = description;
        this.itemType = itemType;
        this.required = required;
        this.useYn = useYn;
        this.survey = survey;
    }

    public SurveyItemEntity(Long id, String itemName, String description, String itemType, boolean required, String useYn, SurveyEntity survey) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.itemType = itemType;
        this.required = required;
        this.useYn = useYn;
        this.survey = survey;
    }
}

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
@Entity(name="surveyResponseItem")
public class SurveyResponseItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String longAnswer; // 장문형
    private String shortAnswer; // 단답형

    private String itemSnapShotDescription;
    private String itemSnapShotName;
    private String itemSnapShotType;
    private boolean itemSnapShotRequired;
    private String itemSnapShotUseYn;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime regDtm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responseId")
    private SurveyResponseEntity response; //

    @OneToMany(mappedBy = "responseItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SurveyResponseOptionEntity> responseOption; //

    public SurveyResponseItemEntity(String longAnswer,String shortAnswer, SurveyResponseEntity response, String itemSnapShotName, String itemSnapShotDescription, String itemSnapShotType, String itemSnapShotUseYn, boolean itemSnapShotRequired) {
        this.longAnswer = longAnswer;
        this.shortAnswer = shortAnswer;
        this.response = response;
        this.itemSnapShotDescription = itemSnapShotDescription;
        this.itemSnapShotName = itemSnapShotName;
        this.itemSnapShotType = itemSnapShotType;
        this.itemSnapShotUseYn = itemSnapShotUseYn;
        this.itemSnapShotRequired = itemSnapShotRequired;
    }
}

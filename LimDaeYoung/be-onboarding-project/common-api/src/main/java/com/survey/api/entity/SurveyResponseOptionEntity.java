package com.survey.api.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="surveyResponseOption")
public class SurveyResponseOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String optionSnapShotName;
    private int optionSnapShotOrder;
    private String optionSnapShotUseYn;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime regDtm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responseItemId")
    private SurveyResponseItemEntity responseItem; // 설문 항목 참조

    public SurveyResponseOptionEntity(SurveyResponseItemEntity responseItem, String optionSnapShotName, int optionSnapShotOrder, String optionSnapShotUseYn) {
        this.responseItem = responseItem;
        this.optionSnapShotName = optionSnapShotName;
        this.optionSnapShotOrder = optionSnapShotOrder;
        this.optionSnapShotUseYn = optionSnapShotUseYn;

    }
}

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

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="surveyResponseItem")
public class SurveyResponseItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String answerText; // 단답형, 장문형의 답변

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime regDtm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responseId")
    private SurveyResponseEntity response; //

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private SurveyItemEntity surveyItem; //

    private SurveyIteSnapshot snapshot; // JSON 형태로 넣기.

    @OneToMany
    private List<SurveyItemSnapshot> itemSnapshots;

    @OneToOne(mappedBy = "responseItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SurveyResponseOptionEntity reponseOption; //

    public SurveyResponseItemEntity(String answerText, SurveyResponseEntity response, SurveyItemEntity surveyItem) {
        this.answerText = answerText;
        this.response = response;
        this.surveyItem = surveyItem;
    }
}

public class AccountJpaEntity {

}

public class AccountAdditionalFieldJpaEntity {

}

package com.practice.survey.responseItem.model.entity;

import com.practice.survey.common.model.entity.BaseTime;
import com.practice.survey.response.model.entity.Response;
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
public class ResponseItem extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_item_id", columnDefinition = "BIGINT")
    private Long resposeItemId;

    @Column(columnDefinition = "TEXT")
    private String responseValue ;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id")
    private Response response;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private SurveyItem item;

}

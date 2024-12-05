package com.practice.survey.surveymngt.model.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class SurveyResponseDto {
    private String surveyName;
    private String surveyDescription;
    private int versionNumber;
    private String itemName;
    private String itemDescription;
    private int itemNumber;
    private String respondentId;
    private String responseValue;

    @QueryProjection
    public SurveyResponseDto(String surveyName, String surveyDescription, int versionNumber,
                             String itemName, String itemDescription, int itemNumber,
                             String respondentId, String responseValue) {
        this.surveyName = surveyName;
        this.surveyDescription = surveyDescription;
        this.versionNumber = versionNumber;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemNumber = itemNumber;
        this.respondentId = respondentId;
        this.responseValue = responseValue;
    }
}

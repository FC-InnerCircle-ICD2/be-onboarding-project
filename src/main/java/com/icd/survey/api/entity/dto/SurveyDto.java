package com.icd.survey.api.entity.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SurveyDto {
    private Long surveySeq;
    private String surveyName;
    private String surveyDescription;
    private String ipAddress;
    private List<SurveyItemDto> surveyItemList;
}

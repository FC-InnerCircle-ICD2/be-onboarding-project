package com.icd.survey.api.entity.survey.dto;

import lombok.*;

import java.util.ArrayList;
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
    private List<SurveyItemDto> surveyItemList = new ArrayList<>();
}

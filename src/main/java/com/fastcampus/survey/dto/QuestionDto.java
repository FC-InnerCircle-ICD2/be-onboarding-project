package com.fastcampus.survey.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionDto {
    private Long id;
    private String qName;
    private String qDescription;
    private String qType;
    private String qMust;
}

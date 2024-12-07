package com.fastcampus.survey.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionDto {
    private Long id;
    private String qName;
    private String qDescription;
    private String qType;
    private List<String> choices;
    private String qMust;
}

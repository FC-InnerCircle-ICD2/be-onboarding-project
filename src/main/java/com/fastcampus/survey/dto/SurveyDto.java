package com.fastcampus.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyDto {
    private Long id;
    private String name;
    private String description;
    private List<QuestionDto> questions;
}

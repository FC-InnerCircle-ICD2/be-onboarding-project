package com.fastcampus.survey.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class SurveyDto {
    private Long id;
    private String name;
    private String description;
    private List<QuestionDto> questions;
}

package com.fastcampus.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAnsDto {
    private Long questionID;
    private String qName;
    private String qDesc;
    private String writer;
    private String qAns;
}

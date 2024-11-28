package com.icd.survey.api.entity.survey.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemAnswerDto {
    private Long answerSeq;
    private String answer;
    private Long optionSeq;
    private String optionAnswer;
    private Boolean isOptionalAnswer;
}

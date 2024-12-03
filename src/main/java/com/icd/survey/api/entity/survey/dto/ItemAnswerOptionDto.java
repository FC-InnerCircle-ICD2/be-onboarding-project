package com.icd.survey.api.entity.survey.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemAnswerOptionDto {
    private Long optionSeq;
    private String option;

    private Long itemSeq;
}

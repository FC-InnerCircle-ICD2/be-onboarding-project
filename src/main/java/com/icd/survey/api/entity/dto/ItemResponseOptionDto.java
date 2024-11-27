package com.icd.survey.api.entity.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemResponseOptionDto {
    private Long optionSeq;
    private String option;
}

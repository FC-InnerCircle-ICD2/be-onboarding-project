package com.icd.survey.api.entity.survey.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SurveyItemDto {
    private Long itemSeq;
    private String itemName;
    private String itemDescription;
    private Integer itemResponseType;
    private Boolean isEssential;
    private List<ItemResponseOptionDto> responseOptionList;

    private Long surveySeq;
}

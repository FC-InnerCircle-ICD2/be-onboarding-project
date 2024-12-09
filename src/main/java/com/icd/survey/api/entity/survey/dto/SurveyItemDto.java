package com.icd.survey.api.entity.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyItemDto {
    private Long itemSeq;
    private String itemName;
    private String itemDescription;
    private List<ItemAnswerDto> itemAnswerList = new ArrayList<>();
    private Integer itemResponseType;
    private Boolean isEssential;
    private List<ItemAnswerOptionDto> responseOptionList = new ArrayList<>();
    private Long surveySeq;
}

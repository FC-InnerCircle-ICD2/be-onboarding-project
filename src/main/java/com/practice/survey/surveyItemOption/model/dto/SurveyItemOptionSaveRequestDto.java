package com.practice.survey.surveyItemOption.model.dto;

import com.practice.survey.surveyItem.model.entity.SurveyItem;
import com.practice.survey.surveyItemOption.model.entity.SurveyItemOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyItemOptionSaveRequestDto {
    private String optionText;  // 선택지 텍스트

    public SurveyItemOption toEntity(SurveyItem surveyItem, int optionNumber){
        return SurveyItemOption.builder()
                .optionText(optionText)
                .item(surveyItem)
                .optionNumber(optionNumber)
                .build();
    }
}

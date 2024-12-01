package com.practice.survey.surveymngt.model.dto;

import com.practice.survey.surveyItem.model.dto.SurveyItemSaveRequestDto;
import com.practice.survey.surveymngt.model.entity.Survey;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveySaveRequestDto {

    private String name;  // 설문조사 이름
    private String description;  // 설문조사 설명
    private List<SurveyItemSaveRequestDto> surveyItems;  // 설문조사 항목 리스트

    public Survey toEntity() {
        return Survey.builder()
                .name(name)
                .description(description)
                .build();
    }
}

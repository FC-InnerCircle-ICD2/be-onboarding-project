package com.practice.survey.surveyItem.model.dto;

import com.practice.survey.surveyItemOption.model.dto.SurveyItemOptionDto;
import com.practice.survey.surveyItem.model.entity.SurveyItem;
import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import com.practice.survey.surveyItem.model.enums.InputType;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyItemDto {

    private Long itemId;

//    private SurveyVersionDto version;
    private Long versionId; // SurveyVersion의 ID만 참조

    private int itemNumber;

    private String name;

    private String description;

    private InputType inputType;

    private boolean isRequired;

//    private List<SurveyItemOptionDto> options;

    public SurveyItem toEntity(SurveyVersion version) {
        return SurveyItem.builder()
                .itemId(itemId)
                .version(version) // SurveyVersion 객체를 외부에서 전달받아 설정
                .itemNumber(itemNumber)
                .name(name)
                .description(description)
                .inputType(inputType)
                .isRequired(isRequired)
                .build();
    }
}

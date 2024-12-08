package com.practice.survey.surveyItem.model.dto;

import com.practice.survey.surveyItem.model.entity.SurveyItem;
import com.practice.survey.surveyItem.model.enums.InputType;
import com.practice.survey.surveyItemOption.model.dto.SurveyItemOptionDto;
import lombok.*;

import java.util.List;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyItemReadDto {

    private Long itemId;

    private InputType inputType;

    private boolean required;

    public static SurveyItemReadDto fromEntity(SurveyItem item) {
        return SurveyItemReadDto.builder()
                .itemId(item.getItemId())
                .inputType(item.getInputType())
                .required(item.isRequired())
                .build();
    }

}

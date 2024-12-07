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

//    private int itemNumber;

    private InputType inputType;

    private boolean isRequired;

//    private List<SurveyItemOptionDto> options;

    public static SurveyItemReadDto fromEntity(SurveyItem item) {
        return SurveyItemReadDto.builder()
                .itemId(item.getItemId())
                .inputType(item.getInputType())
                .isRequired(item.getIsRequired())
//                .options(item.getOptions())
                .build();
    }

    public boolean getIsRequired() {
        return isRequired;
    }

}

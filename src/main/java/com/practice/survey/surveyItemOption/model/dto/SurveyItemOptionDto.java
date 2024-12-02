package com.practice.survey.surveyItemOption.model.dto;

import com.practice.survey.surveyItem.model.entity.SurveyItem;
import com.practice.survey.surveyItemOption.model.entity.SurveyItemOption;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyItemOptionDto {

    private Long optionId;  // BIGINT에 대응하는 Long 타입

    private Long itemId;  // SurveyItem의 ID만 참조
//    private SurveyItemDto item;

    private int optionNumber;

    private String optionText;

    public SurveyItemOption toEntity(SurveyItem item) {
        return SurveyItemOption.builder()
                .optionId(optionId)
                .item(item) // SurveyItem 객체를 외부에서 전달받아 설정
                .optionNumber(optionNumber)
                .optionText(optionText)
                .build();
    }
}

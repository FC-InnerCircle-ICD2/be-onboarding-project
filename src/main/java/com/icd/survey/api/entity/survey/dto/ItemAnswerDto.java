package com.icd.survey.api.entity.survey.dto;

import com.icd.survey.api.enums.survey.ResponseType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemAnswerDto {
    private Long itemSeq;
    private Long answerSeq;
    private String answer;
    private Long optionSeq;
    private String optionAnswer;
    private Boolean isOptionalAnswer;
    private Integer responseType;

    private String shortAnswer;
    private String longAnswer;
    private Long singleChoiceOptionSeq;
    private Long multiChoiceOptionSeq;
}

package com.icd.survey.api.controller.survey.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswerRequest {
    private Long itemSeq;
    private String answer;
    private Long optionalAnswer;
}

package com.icd.survey.api.controller.survey.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitSurveyRequest {
    @NotNull(message = "설문조사 식별자는 필수 항목입니다.")
    private Long surveySeq;

    List<SurveyItemRequest> surveyItemList;
}

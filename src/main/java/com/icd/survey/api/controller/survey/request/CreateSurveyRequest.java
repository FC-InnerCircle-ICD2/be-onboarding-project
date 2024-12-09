package com.icd.survey.api.controller.survey.request;

import com.icd.survey.api.entity.survey.dto.SurveyDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateSurveyRequest {

    private String ipAddress;

    @NotBlank(message = "설문조사 이름을 입력해 주세요.")
    @Size(min = 1, max = 255, message = "설문조사 이름의 길이는 최소 1 ~ 최대 255 자 입니다.")
    private String surveyName;

    @NotBlank(message = "설문조사 설명에 대해 입력해 주세요.")
    @Size(min = 1, max = 1000, message = "설문조사 설명의 길이는 최소 1 ~ 최대 1000 자 입니다.")
    private String surveyDescription;

    @NotNull(message = "항목들을 구성해 주세요.")
    @Size(min = 1, max = 10, message = "항목은 1개에서 10개 까지 입력 가능합니다.")
    @Valid
    private List<SurveyItemRequest> surveyItemList = new ArrayList<>();


    public SurveyDto createSurveyDtoRequest() {
        return SurveyDto
                .builder()
                .ipAddress(ipAddress)
                .surveyName(surveyName)
                .surveyDescription(surveyDescription)
                .build();
    }

}

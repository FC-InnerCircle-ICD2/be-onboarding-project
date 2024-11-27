package com.icd.survey.api.dto.survey.request;

import com.icd.survey.api.entity.dto.SurveyDto;
import com.icd.survey.exception.ApiException;
import com.icd.survey.exception.response.emums.ExceptionResponseType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateSurveyUpdateRequest {

    @NotNull(message = "설문조사 번호는 필수요소입니다.")
    private Long surveySeq;

    private String surveyName;

    private String surveyDescription;

    @NotNull(message = "항목들을 구성해 주세요.")
    @Valid
    private List<SurveyItemRequest> surveyItemList = new ArrayList<>();

    public SurveyDto createSurveyDtoRequest() {
        return SurveyDto
                .builder()
                .surveyName(surveyName)
                .surveyDescription(surveyDescription)
                .build();
    }

}

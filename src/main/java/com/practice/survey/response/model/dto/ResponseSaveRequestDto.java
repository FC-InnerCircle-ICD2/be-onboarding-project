package com.practice.survey.response.model.dto;

import com.practice.survey.responseItem.model.dto.ResponseItemSaveRequestDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSaveRequestDto {

    @NotNull(message = "설문조사 아이디는 필수 입력 값입니다.")
    private Long surveyId;

//    @NotEmpty(message = "설문조사 이름은 필수 입력 값입니다.")
//    private String surveyName;

    @NotNull(message = "설문조사 버전은 필수 입력 값입니다.")
    private int surveyVersionNumber;

    @NotEmpty(message = "응답자 아이디는 필수 입력 값입니다.")
    private String respondentId;

    @Size(min = 1, max = 10, message = "설문조사는 1에서 10개까지의 항목만 가질 수 있습니다.")
    private List<ResponseItemSaveRequestDto> responseItems;
}

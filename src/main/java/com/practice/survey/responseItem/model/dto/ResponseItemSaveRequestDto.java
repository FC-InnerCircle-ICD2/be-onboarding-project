package com.practice.survey.responseItem.model.dto;

import com.practice.survey.surveyItem.model.enums.InputType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseItemSaveRequestDto {

    @NotEmpty
    private Long itemId;  // 설문조사 항목 아이디

//    private InputType inputType;

//    private boolean isRequired;

    private List<String> responseValue;  // 응답 값

}

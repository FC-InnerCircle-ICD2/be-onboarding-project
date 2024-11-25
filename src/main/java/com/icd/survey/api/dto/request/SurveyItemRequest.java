package com.icd.survey.api.dto.request;

import com.icd.survey.api.enums.ResponseType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SurveyItemRequest {

    @NotBlank(message = "항목의 이름을 입력해 주세요.")
    @Size(min = 1, max = 255, message = "설문조사 이름의 길이를 확인하세요.")
    private String itemName;

    @NotBlank(message = "항목의 설명에 대해 입력해 주세요.")
    @Size(min = 1, max = 1000, message = "설문조사 설명의 길이를 확인하세요.")
    private String itemDescription;

    @NotNull(message = "응답 방식 선택해 주세요.")
    @Min(value = 1, message = "응답 방식을 확인해주세요.")
    @Max(value = 4, message = "응답 방식을 확인해주세요.")
    private Integer itemResponseType;


    private List<ItemOptionRequest> optionList = new ArrayList<>();

    private Boolean isEssential;

    public void validationCheck() throws IllegalArgumentException {
        if (Boolean.TRUE.equals(isChoiceType()) && optionList.isEmpty()) {
            throw new IllegalArgumentException("선택 항목의 경우 옵션을 입력해야만 합니다.");
        }
    }

    public Boolean isChoiceType() {
        return this.itemResponseType.equals(ResponseType.SINGLE_CHOICE.getType())
                || this.itemResponseType.equals(ResponseType.MULTI_CHOICE.getType());
    }
}

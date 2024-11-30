package com.icd.survey.api.dto.survey.request;

import com.icd.survey.api.entity.survey.dto.ItemAnswerOptionDto;
import com.icd.survey.exception.ApiException;
import com.icd.survey.exception.response.emums.ExceptionResponseType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemOptionRequest {
    private String option;

    public void validationCheck() {
        if (option.isEmpty()) {
            throw new ApiException(ExceptionResponseType.ILLEGAL_ARGUMENT , "옵션 정보를 확인하세요.");
        }
    }

    public ItemAnswerOptionDto createItemResponseOptionDto() {
        return ItemAnswerOptionDto
                .builder()
                .option(option)
                .build();
    }

}

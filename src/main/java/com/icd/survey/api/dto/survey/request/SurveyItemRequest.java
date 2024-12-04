package com.icd.survey.api.dto.survey.request;

import com.icd.survey.api.entity.survey.dto.ItemAnswerDto;
import com.icd.survey.api.entity.survey.dto.SurveyItemDto;
import com.icd.survey.api.enums.survey.ResponseType;
import com.icd.survey.exception.ApiException;
import com.icd.survey.exception.response.emums.ExceptionResponseType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SurveyItemRequest {

    private Long surveySeq;

    private Long itemSeq;

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

    private List<ItemAnswerDto> answerList;
    /* create, update survey 를 위한 항목 리스트 */
    private List<ItemOptionRequest> optionList;

    /* submit survey 에서 다중 선택형 응답을 위한 리스트 */
    private List<SurveyAnswerRequest> optionalAnswerList;
    /* 단답형, 서술형, 단일 선택형 응답을 위한 객체 */
    private SurveyAnswerRequest surveyAnswerRequest;

    @Builder.Default
    private Boolean isEssential = Boolean.FALSE;

    public void validationCheck() {
        if (Boolean.TRUE.equals(isChoiceType()) && (optionList == null || optionList.isEmpty())) {
            throw new ApiException(ExceptionResponseType.VALIDATION_EXCEPTION, "선택 항목의 경우 옵션을 입력해야만 합니다.");
        } else if(Boolean.FALSE.equals(isChoiceType()) && (optionList != null && Boolean.FALSE.equals(optionList.isEmpty()))) {
            throw new ApiException(ExceptionResponseType.VALIDATION_EXCEPTION, "응답 옵션은 선택 항목에만 필요합니다.");
        }
    }

    public void answerValidationCheck() {
        if (Boolean.TRUE.equals(itemResponseType.equals(ResponseType.MULTI_CHOICE.getType()))) {
            if (optionalAnswerList == null || optionalAnswerList.isEmpty()) {
                throw new ApiException(ExceptionResponseType.ILLEGAL_ARGUMENT, "다중 선택 항목의 응답값을 확인해 주세요.");
            }
        } else {
            if (surveyAnswerRequest == null) {
                throw new ApiException(ExceptionResponseType.ILLEGAL_ARGUMENT, "항목의 응답값을 확인해 주세요.");
            }
        }

    }

    public Boolean isChoiceType() {
        return this.itemResponseType.equals(ResponseType.SINGLE_CHOICE.getType())
                || this.itemResponseType.equals(ResponseType.MULTI_CHOICE.getType());
    }

    public SurveyItemDto createSurveyItemDtoRequest() {
        return SurveyItemDto
                .builder()
                .surveySeq(surveySeq)
                .itemName(itemName)
                .itemDescription(itemDescription)
                .itemResponseType(itemResponseType)
                .isEssential(isEssential)
                .build();
    }

}

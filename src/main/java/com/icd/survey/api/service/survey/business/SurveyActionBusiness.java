package com.icd.survey.api.service.survey.business;

import com.icd.survey.api.dto.survey.request.ItemOptionRequest;
import com.icd.survey.api.dto.survey.request.SurveyAnswerRequest;
import com.icd.survey.api.dto.survey.request.SurveyItemRequest;
import com.icd.survey.api.entity.survey.ItemAnswer;
import com.icd.survey.api.entity.survey.ItemAnswerOption;
import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.SurveyItem;
import com.icd.survey.api.entity.survey.dto.ItemAnswerDto;
import com.icd.survey.api.entity.survey.dto.ItemAnswerOptionDto;
import com.icd.survey.api.entity.survey.dto.SurveyDto;
import com.icd.survey.api.entity.survey.dto.SurveyItemDto;
import com.icd.survey.api.enums.survey.ResponseType;
import com.icd.survey.api.repository.survey.AnswerOptionRepository;
import com.icd.survey.api.repository.survey.ItemAnswerRepository;
import com.icd.survey.api.repository.survey.SurveyItemRepository;
import com.icd.survey.api.repository.survey.SurveyRepository;
import com.icd.survey.api.repository.survey.query.SurveyQueryRepository;
import com.icd.survey.exception.ApiException;
import com.icd.survey.exception.response.emums.ExceptionResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyActionBusiness {
    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final ItemAnswerRepository itemAnswerRepository;
    private final AnswerOptionRepository answerOptionRepository;

    private final SurveyQueryBusiness surveyQueryBusiness;
    private final SurveyQueryRepository surveyQueryRepository;

    public Survey saveSurvey(SurveyDto surveyDto) {
        return surveyRepository.save(Survey.createSurveyRequest(surveyDto));
    }

    public SurveyItem saveSurveyItem(SurveyItemDto surveyItemDto) {
        return surveyItemRepository.save(SurveyItem.createSurveyItemRequest(surveyItemDto));
    }

    public ItemAnswerOption saveAnswerOption(ItemAnswerOptionDto responseOptionDto) {
        return answerOptionRepository.save(ItemAnswerOption.createItemResponseOptionRequest(responseOptionDto));
    }

    public void updateSurveyItemAsDisabled(Long surveySeq) {
        surveyQueryRepository.updateSurveyItemAsDisabled(surveySeq);
    }

    public void saveAnswer(ItemAnswerDto answerDto) {
        itemAnswerRepository.save(ItemAnswer.createItemResponseRequest(answerDto));
    }

    public void saveSurveyItemList(List<SurveyItemRequest> itemRequsList, Long surveySeq) {
        itemRequsList.forEach(x -> {
            x.validationCheck();

            SurveyItemDto dto = x.createSurveyItemDtoRequest();
            dto.setSurveySeq(surveySeq);
            Long itemSeq = saveSurveyItem(dto).getItemSeq();

            if (Boolean.TRUE.equals(x.isChoiceType())) {
                saveItemOptionList(x.getOptionList(), itemSeq);
            }
        });
    }

    public void saveItemOptionList(List<ItemOptionRequest> optionDtoList, Long itemSeq) {
        optionDtoList.forEach(x -> {
            ItemAnswerOptionDto dto = x.createItemResponseOptionDto();
            dto.setItemSeq(itemSeq);
            saveAnswerOption(dto);
        });
    }

    public void answerSurveyItem(SurveyItemRequest itemRequest) {

        SurveyItem item = surveyQueryBusiness.findSurveyItemById(itemRequest.getItemSeq())
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

        itemRequest.setItemResponseType(item.getItemResponseType());

        itemRequest.answerValidationCheck();

        if (Boolean.TRUE.equals(itemRequest.isChoiceType())) {

            if (itemRequest.getItemResponseType().equals(ResponseType.SINGLE_CHOICE.getType())) {
                ItemAnswerOption option = surveyQueryBusiness.findOptionByIdAndItemSeq(itemRequest.getSurveyAnswerRequest().getOptionalAnswer(), item.getItemSeq())
                        .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND, "존재하지 않는 선택항목 입니다."));

                saveAnswer(ItemAnswerDto
                        .builder()
                        .responseType(item.getItemResponseType())
                        .itemSeq(item.getItemSeq())
                        .isOptionalAnswer(Boolean.TRUE)
                        .optionSeq(itemRequest.getSurveyAnswerRequest().getOptionalAnswer())
                        .optionAnswer(option.getOption())
                        .build());
            } else if (itemRequest.getItemResponseType().equals(ResponseType.MULTI_CHOICE.getType())) {
                itemRequest.getOptionalAnswerList()
                        .forEach(x -> {
                            ItemAnswerOption option = surveyQueryBusiness.findOptionByIdAndItemSeq(x.getOptionalAnswer(), item.getItemSeq())
                                    .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND, "존재하지 않는 선택항목 입니다."));

                            ItemAnswerDto answerDto =
                                    ItemAnswerDto
                                            .builder()
                                            .responseType(item.getItemResponseType())
                                            .itemSeq(item.getItemSeq())
                                            .isOptionalAnswer(Boolean.TRUE)
                                            .optionSeq(x.getOptionalAnswer())
                                            .optionAnswer(option.getOption())
                                            .build();
                            saveAnswer(answerDto);
                        });
            }
        } else {
            SurveyAnswerRequest answerRequest = itemRequest.getSurveyAnswerRequest();
            saveAnswer(ItemAnswerDto
                    .builder()
                    .responseType(item.getItemResponseType())
                    .itemSeq(item.getItemSeq())
                    .isOptionalAnswer(Boolean.FALSE)
                    .answer(answerRequest.getAnswer())
                    .build());
        }
    }

}

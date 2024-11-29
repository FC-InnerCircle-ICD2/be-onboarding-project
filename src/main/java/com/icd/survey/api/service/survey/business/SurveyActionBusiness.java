package com.icd.survey.api.service.survey.business;

import com.icd.survey.api.dto.survey.request.ItemOptionRequest;
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
import com.icd.survey.exception.ApiException;
import com.icd.survey.exception.response.emums.ExceptionResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyActionBusiness {
    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final ItemAnswerRepository itemAnswerRepository;
    private final AnswerOptionRepository answerOptionRepository;

    private final SurveyQueryBusiness surveyQueryBusiness;

    public Survey saveSurvey(SurveyDto surveyDto) {
        return surveyRepository.save(Survey.createSurveyRequest(surveyDto));
    }

    public SurveyItem saveSurveyItem(SurveyItemDto surveyItemDto) {
        return surveyItemRepository.save(SurveyItem.createSurveyItemRequest(surveyItemDto));
    }

    public ItemAnswerOption saveItemResponseOption(ItemAnswerOptionDto responseOptionDto) {
        return answerOptionRepository.save(ItemAnswerOption.createItemResponseOptionRequest(responseOptionDto));
    }

    public void disableItemList(Long surveySeq) {
        Optional<List<SurveyItem>> optionalSurveyItemList = surveyQueryBusiness.findAllSurveyItem(surveySeq);

        if (Boolean.TRUE.equals(optionalSurveyItemList.isPresent())) {
            List<SurveyItem> surveyItemList = optionalSurveyItemList.get();
            surveyItemList.forEach(SurveyItem::disable);
        }
    }

    public void saveSurveyItemList(List<SurveyItemRequest> itemDtoList, Long surveySeq) {
        itemDtoList.forEach(x -> {
            x.validationCheck();

            SurveyItemDto dto = x.createSurveyItemDtoRequest();
            dto.setSurveySeq(surveySeq);
            Long itemSeq = surveyItemRepository.save(SurveyItem.createSurveyItemRequest(dto)).getItemSeq();

            if (Boolean.TRUE.equals(x.isChoiceType())) {
                saveItemOptionList(x.getOptionList(), itemSeq);
            }
        });
    }

    public void saveItemOptionList(List<ItemOptionRequest> optionDtoList, Long itemSeq) {
        optionDtoList.forEach(x -> {

            ItemAnswerOptionDto dto = x.createItemREsponseOptionDto();
            dto.setItemSeq(itemSeq);
            answerOptionRepository.save(ItemAnswerOption.createItemResponseOptionRequest(dto));
        });
    }

    public void answerSurveyItem(SurveyItemRequest itemRequest) {

        surveyQueryBusiness.findSurveyItemById(itemRequest.getSurveySeq())
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

        if (itemRequest.isChoiceType() && itemRequest.getOptionalAnswerList().isEmpty()) {
            throw new ApiException(ExceptionResponseType.ILLEGAL_ARGUMENT, "선택형 설문 옵션을 확인하세요.");
        }

        if (Boolean.TRUE.equals(itemRequest.isChoiceType())) {
            if (itemRequest.getItemResponseType().equals(ResponseType.SINGLE_CHOICE.getType())) {
                ItemAnswerOption option = answerOptionRepository.findById(itemRequest.getSurveyAnswerRequest().getOptionalAnswer())
                        .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

                saveAnswer(ItemAnswerDto
                        .builder()
                        .itemSeq(itemRequest.getItemSeq())
                        .isOptionalAnswer(Boolean.TRUE)
                        .optionSeq(itemRequest.getSurveyAnswerRequest().getOptionalAnswer())
                        .optionAnswer(option.getOption())
                        .build());
            } else if (itemRequest.getItemResponseType().equals(ResponseType.MULTI_CHOICE.getType())) {
                // todo : optionList 가져 와서 save.
            }
        } else {
            saveAnswer(ItemAnswerDto
                    .builder()
                    .itemSeq(itemRequest.getItemSeq())
                    .isOptionalAnswer(Boolean.FALSE)
                    .answer(itemRequest.getSurveyAnswerRequest().getAnswer())
                    .build());
        }

    }

    public void saveAnswer(ItemAnswerDto answerDto) {
        itemAnswerRepository.save(ItemAnswer.createItemResponseRequest(answerDto));
    }

}

package com.icd.survey.api.service.survey.business;

import com.icd.survey.api.dto.survey.request.ItemOptionRequest;
import com.icd.survey.api.dto.survey.request.SurveyItemRequest;
import com.icd.survey.api.entity.survey.ItemResponseOption;
import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.SurveyItem;
import com.icd.survey.api.entity.survey.dto.ItemResponseOptionDto;
import com.icd.survey.api.entity.survey.dto.SurveyDto;
import com.icd.survey.api.entity.survey.dto.SurveyItemDto;
import com.icd.survey.api.repository.survey.ItemResponseRepository;
import com.icd.survey.api.repository.survey.ResponseOptionRepository;
import com.icd.survey.api.repository.survey.SurveyItemRepository;
import com.icd.survey.api.repository.survey.SurveyRepository;
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
    private final ItemResponseRepository itemResponseRepository;
    private final ResponseOptionRepository responseOptionRepository;

    private final SurveyQueryBusiness surveyQueryBusiness;

    public Survey saveSurvey(SurveyDto surveyDto) {
        return surveyRepository.save(Survey.createSurveyRequest(surveyDto));
    }

    public SurveyItem saveSurveyItem(SurveyItemDto surveyItemDto) {
        return surveyItemRepository.save(SurveyItem.createSurveyItemRequest(surveyItemDto));
    }

    public ItemResponseOption saveItemResponseOption(ItemResponseOptionDto responseOptionDto) {
        return responseOptionRepository.save(ItemResponseOption.createItemResponseOptionRequest(responseOptionDto));
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

            ItemResponseOptionDto dto = x.createItemREsponseOptionDto();
            dto.setItemSeq(itemSeq);
            responseOptionRepository.save(ItemResponseOption.createItemResponseOptionRequest(dto));
        });
    }
}

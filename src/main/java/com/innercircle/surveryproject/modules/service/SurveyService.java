package com.innercircle.surveryproject.modules.service;

import com.innercircle.surveryproject.infra.exceptions.InvalidInputException;
import com.innercircle.surveryproject.modules.dto.SurveyCreateDto;
import com.innercircle.surveryproject.modules.dto.SurveyDto;
import com.innercircle.surveryproject.modules.dto.SurveyItemDto;
import com.innercircle.surveryproject.modules.dto.SurveyUpdateDto;
import com.innercircle.surveryproject.modules.entity.Survey;
import com.innercircle.surveryproject.modules.entity.SurveyItem;
import com.innercircle.surveryproject.modules.repository.SurveyItemRepository;
import com.innercircle.surveryproject.modules.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    private final SurveyItemRepository surveyItemRepository;

    /**
     * 설문조사 등록 메소드
     *
     * @param surveyCreateDto
     * @return
     */
    @Transactional
    public SurveyDto createSurvey(SurveyCreateDto surveyCreateDto) {

        if (surveyCreateDto.getSurveyItemDtoList().isEmpty() || surveyCreateDto.getSurveyItemDtoList().size() > 10) {
            throw new InvalidInputException("설문조사 항목은 1~10개까지 등록가능합니다.");
        }

        Survey survey = Survey.from(surveyCreateDto);

        List<SurveyItem> surveyItemList = surveyCreateDto.getSurveyItemDtoList().stream().map(SurveyItem::from).toList();
        surveyItemList.forEach(surveyItem -> surveyItem.setSurvey(survey));
        survey.setSurveyItemList(surveyItemList);

        surveyRepository.save(survey);
        surveyItemRepository.saveAll(surveyItemList);

        return SurveyDto.from(survey);
    }

    /**
     * 설문조사 수정 메소드
     *
     * @param surveyUpdateDto
     * @return
     */
    @Transactional
    public SurveyDto updateSurvey(SurveyUpdateDto surveyUpdateDto) {

        Survey survey =
            surveyRepository.findById(surveyUpdateDto.getId()).orElseThrow(() -> new InvalidInputException("일치하는 설문조사를 찾을 수 없습니다."));

        List<SurveyItemDto> surveyItemDtoList = surveyUpdateDto.getSurveyItemDtoList();

        List<Long> existingSurveyItemIds = survey.getSurveyItemList().stream()
            .map(SurveyItem::getId)
            .toList();

        for (SurveyItemDto surveyItemDto : surveyItemDtoList) {

            if (isExistSurveyItem(existingSurveyItemIds, surveyItemDto)) {
                SurveyItem existingItem = survey.getSurveyItemById(surveyItemDto.getSurveyItemId());
                if (!existingItem.equalsDto(surveyItemDto)) {
                    existingItem.updateFromDto(surveyItemDto);
                }
                existingItem.setActive(true);
            } else {
                SurveyItem newSurveyItem = SurveyItem.from(surveyItemDto);
                newSurveyItem.setActive(true);
                survey.addSurveyItem(newSurveyItem);
            }
        }

        List<SurveyItem> toDeactivateSurveyItems = survey.getSurveyItemList().stream()
            .filter(item -> surveyItemDtoList.stream()
                .noneMatch(dto -> dto.getSurveyItemId() != null && dto.getSurveyItemId().equals(item.getId())))
            .toList();

        for (SurveyItem itemToDeactivate : toDeactivateSurveyItems) {
            itemToDeactivate.setActive(false);
        }

        return SurveyDto.from(survey);
    }

    private boolean isExistSurveyItem(List<Long> surveyItemList, SurveyItemDto surveyItemDto) {
        return surveyItemDto.getSurveyItemId() != null && surveyItemList.contains(surveyItemDto.getSurveyItemId());
    }

    /**
     * 설문조사 조회 메소드
     *
     * @param surveyId
     * @return
     */
    @Transactional(readOnly = true)
    public SurveyDto retrieveSurvey(Long surveyId) {

        Survey survey =
            surveyRepository.findById(surveyId).orElseThrow(() -> new InvalidInputException("일치하는 설문조사를 찾을 수 없습니다."));

        return SurveyDto.from(survey);
    }

}

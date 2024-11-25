package com.innercircle.surveryproject.modules.service;

import com.innercircle.surveryproject.infra.exceptions.InvalidInputException;
import com.innercircle.surveryproject.modules.dto.SurveyCreateDto;
import com.innercircle.surveryproject.modules.dto.SurveyDto;
import com.innercircle.surveryproject.modules.entity.Survey;
import com.innercircle.surveryproject.modules.entity.SurveyItem;
import com.innercircle.surveryproject.modules.repository.SurveyItemRepository;
import com.innercircle.surveryproject.modules.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public SurveyDto createSurvey(SurveyCreateDto surveyCreateDto) {

        List<SurveyItem> surveyItemList = surveyCreateDto.getSurveyItemDtoList().stream().map(SurveyItem::from).toList();

        if (surveyItemList.isEmpty() || surveyItemList.size() > 10) {
            throw new InvalidInputException("설문조사 항목은 1~10개까지 등록가능합니다.");
        }

        surveyItemRepository.saveAll(surveyItemList);

        Survey survey = Survey.from(surveyCreateDto);
        survey.setSurveyItemList(surveyItemList);
        surveyRepository.save(survey);

        return SurveyDto.from(survey);
    }

}

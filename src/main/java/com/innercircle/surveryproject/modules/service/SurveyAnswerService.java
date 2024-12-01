package com.innercircle.surveryproject.modules.service;

import com.innercircle.surveryproject.infra.exceptions.InvalidInputException;
import com.innercircle.surveryproject.modules.dto.SurveyAnswerCreateDto;
import com.innercircle.surveryproject.modules.dto.SurveyAnswerDto;
import com.innercircle.surveryproject.modules.dto.SurveyItemResponseDto;
import com.innercircle.surveryproject.modules.entity.Survey;
import com.innercircle.surveryproject.modules.entity.SurveyAnswer;
import com.innercircle.surveryproject.modules.entity.SurveyItem;
import com.innercircle.surveryproject.modules.repository.SurveyAnswerRepository;
import com.innercircle.surveryproject.modules.repository.SurveyItemRepository;
import com.innercircle.surveryproject.modules.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SurveyAnswerService {

    private final SurveyRepository surveyRepository;

    private final SurveyItemRepository surveyItemRepository;

    private final SurveyAnswerRepository surveyAnswerRepository;

    /**
     * 설문조사 응답 생성 메소드
     *
     * @param surveyAnswerCreateDto
     * @return
     */
    public SurveyAnswerDto createSurveyAnswer(SurveyAnswerCreateDto surveyAnswerCreateDto) {

        try {
            Long.valueOf(surveyAnswerCreateDto.getPhoneNumber());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("휴대폰 번호는 국가번호+전화번호를 포함한 숫자만 입력 가능합니다. ex) 8201012341234");
        }

        Survey survey = surveyRepository.findById(surveyAnswerCreateDto.getSurveyId()).orElseThrow(() -> new InvalidInputException(
            "일치하는 설문조사를 찾을 수 없습니다."));

        List<SurveyItemResponseDto> surveyItemResponseDtoList = surveyAnswerCreateDto.getSurveyItemResponseDtoList();

        Map<Long, String> surveyAnswerMap = new HashMap<>();
        for (SurveyItemResponseDto surveyItemResponseDto : surveyItemResponseDtoList) {
            Optional<SurveyItem> optionalSurveyAnswer =
                surveyItemRepository.findBySurveyIdAndId(surveyAnswerCreateDto.getSurveyId(),
                                                         surveyItemResponseDto.getSurveyItemId());

            if (optionalSurveyAnswer.isEmpty()) {
                throw new InvalidInputException("일치하는 설문조사 항목을 찾을 수 없습니다.");
            }

            surveyAnswerMap.put(surveyItemResponseDto.getSurveyItemId(), surveyItemResponseDto.getAnswer());
        }

        SurveyAnswer surveyAnswer = SurveyAnswer.from(surveyAnswerCreateDto, surveyAnswerMap);
        surveyAnswer.setSurvey(survey);
        surveyAnswerRepository.save(surveyAnswer);

        return SurveyAnswerDto.from(surveyAnswer);
    }

}

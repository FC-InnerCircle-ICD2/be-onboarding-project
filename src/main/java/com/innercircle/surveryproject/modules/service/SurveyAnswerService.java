package com.innercircle.surveryproject.modules.service;

import com.innercircle.surveryproject.infra.exceptions.InvalidInputException;
import com.innercircle.surveryproject.modules.dto.*;
import com.innercircle.surveryproject.modules.entity.Survey;
import com.innercircle.surveryproject.modules.entity.SurveyAnswer;
import com.innercircle.surveryproject.modules.entity.SurveyAnswerMapValue;
import com.innercircle.surveryproject.modules.entity.SurveyItem;
import com.innercircle.surveryproject.modules.repository.SurveyAnswerMapValueRepository;
import com.innercircle.surveryproject.modules.repository.SurveyAnswerRepository;
import com.innercircle.surveryproject.modules.repository.SurveyItemRepository;
import com.innercircle.surveryproject.modules.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SurveyAnswerService {

    private final SurveyRepository surveyRepository;

    private final SurveyItemRepository surveyItemRepository;

    private final SurveyAnswerRepository surveyAnswerRepository;

    private final SurveyAnswerMapValueRepository surveyAnswerMapValueRepository;

    /**
     * 설문조사 응답 생성 메소드
     *
     * @param surveyAnswerCreateDto
     * @return
     */
    @Transactional
    public SurveyAnswerDto createSurveyAnswer(SurveyAnswerCreateDto surveyAnswerCreateDto) {

        List<SurveyItemResponseDto> surveyItemResponseDtoList = surveyAnswerCreateDto.getSurveyItemResponseDtoList();

        List<SurveyAnswerMapValue> surveyAnswerMapValueList = new ArrayList<>();
        for (SurveyItemResponseDto surveyItemResponseDto : surveyItemResponseDtoList) {
            Optional<SurveyItem> optionalSurveyItem =
                    surveyItemRepository.findBySurvey_IdAndId(surveyAnswerCreateDto.getSurveyId(),
                            surveyItemResponseDto.getSurveyItemId());

            if (optionalSurveyItem.isEmpty()) {
                throw new InvalidInputException("일치하는 설문조사 항목을 찾을 수 없습니다.");
            }

            SurveyItem surveyItem = optionalSurveyItem.get();
            if (Boolean.TRUE.equals(surveyItem.getRequired()) && (surveyItemResponseDto.getAnswer() == null
                    || surveyItemResponseDto.getAnswer().isEmpty())) {
                throw new InvalidInputException("필수 항목을 입력해주세요.");
            }

            surveyAnswerMapValueList.add(SurveyAnswerMapValue.from(surveyItemResponseDto.getSurveyItemId(),
                    surveyItemResponseDto.getAnswer()));
        }

        SurveyAnswer surveyAnswer = SurveyAnswer.from(surveyAnswerCreateDto);
        surveyAnswer.getSurveyAnswerDetails().addAll(surveyAnswerMapValueList);
        surveyAnswerRepository.save(surveyAnswer);

        return SurveyAnswerDto.from(surveyAnswer);
    }

    /**
     * 설문조사 항목 조회
     *
     * @param surveyAnswerId
     * @return
     */
    @Transactional(readOnly = true)
    public List<SurveyAnswerKeywordDto> retrieveSurveyAnswerKeywords(Long surveyAnswerId) {

        Survey survey = surveyRepository.findById(surveyAnswerId).orElseThrow(() -> new InvalidInputException("일치하는 설문조사가 없습니다."));

        return survey.getSurveyItemList().stream().map(SurveyAnswerKeywordDto::from).toList();
    }

    /**
     * 설문조사 응답 조회 메소드
     *
     * @param surveyAnswerId
     * @param surveyItemId
     * @param surveyItemAnswer
     * @return
     */
    @Transactional(readOnly = true)
    public List<SurveyAnswerResponseDto> retrieveSurveyAnswer(Long surveyId, Long surveyItemId, String surveyItemAnswer) {

        List<SurveyAnswerMapValue> surveyAnswerMapValueList = surveyAnswerMapValueRepository.findBySurveyAnswerId(surveyId);


        if (surveyAnswerMapValueList.isEmpty()) {
            throw new InvalidInputException("일치하는 설문조사를 찾을 수 없습니다.");
        }

        return surveyAnswerMapValueList.stream()
                .filter(surveyAnswerMapValue ->
                        (ObjectUtils.isEmpty(surveyItemId) || surveyAnswerMapValue.getSurveyItemId().equals(surveyItemId)) &&
                                (ObjectUtils.isEmpty(surveyItemAnswer) || surveyAnswerMapValue.getResponses().contains(surveyItemAnswer))

                )
                .map(entry -> SurveyAnswerResponseDto.of(
                        entry.getSurveyAnswer().getSurveyId(),
                        entry.getSurveyAnswer().getPhoneNumber(),
                        entry.getSurveyItemId(),
                        entry.getResponses()
                ))
                .toList();

    }

}

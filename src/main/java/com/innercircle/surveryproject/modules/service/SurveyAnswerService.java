package com.innercircle.surveryproject.modules.service;

import com.innercircle.surveryproject.infra.exceptions.InvalidInputException;
import com.innercircle.surveryproject.modules.dto.*;
import com.innercircle.surveryproject.modules.entity.Survey;
import com.innercircle.surveryproject.modules.entity.SurveyAnswer;
import com.innercircle.surveryproject.modules.entity.SurveyItem;
import com.innercircle.surveryproject.modules.repository.SurveyAnswerRepository;
import com.innercircle.surveryproject.modules.repository.SurveyItemRepository;
import com.innercircle.surveryproject.modules.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public SurveyAnswerDto createSurveyAnswer(SurveyAnswerCreateDto surveyAnswerCreateDto) {

        Survey survey = surveyRepository.findById(surveyAnswerCreateDto.getSurveyId()).orElseThrow(() -> new InvalidInputException(
                "일치하는 설문조사를 찾을 수 없습니다."));

        List<SurveyItemResponseDto> surveyItemResponseDtoList = surveyAnswerCreateDto.getSurveyItemResponseDtoList();

        Map<Long, String> surveyAnswerMap = new HashMap<>();
        for (SurveyItemResponseDto surveyItemResponseDto : surveyItemResponseDtoList) {
            Optional<SurveyItem> optionalSurveyItem =
                    surveyItemRepository.findBySurvey_IdAndId(surveyAnswerCreateDto.getSurveyId(),
                            surveyItemResponseDto.getSurveyItemId());

            if (optionalSurveyItem.isEmpty()) {
                throw new InvalidInputException("일치하는 설문조사 항목을 찾을 수 없습니다.");
            }

            SurveyItem surveyItem = optionalSurveyItem.get();
            if (surveyItem.getRequired() && (surveyItemResponseDto.getAnswer() == null || surveyItemResponseDto.getAnswer().isEmpty())) {
                throw new InvalidInputException("필수 항목을 입력해주세요.");
            }

            surveyAnswerMap.put(surveyItemResponseDto.getSurveyItemId(), surveyItemResponseDto.getAnswer());
        }

        SurveyAnswer surveyAnswer = SurveyAnswer.from(surveyAnswerCreateDto, surveyAnswerMap);
        surveyAnswer.setSurvey(survey);
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
    public List<SurveyAnswerResponseDto> retrieveSurveyAnswer(Long surveyAnswerId, Long surveyItemId, String surveyItemAnswer) {

        Survey survey =
                surveyRepository.findById(surveyAnswerId).orElseThrow(() -> new InvalidInputException("일치하는 설문조사가 없습니다."));

        return survey.getSurveyAnswerList().stream()
                .flatMap(surveyAnswer -> surveyAnswer.getSurveyAnswerMap().entrySet().stream()
                        .filter(entry -> (surveyItemId == null || entry.getKey().equals(surveyItemId)) &&
                                (surveyItemAnswer == null || entry.getValue().equals(surveyItemAnswer)))
                        .map(entry -> SurveyAnswerResponseDto.of(surveyAnswer.getSurveyId(),
                                surveyAnswer.getPhoneNumber(),
                                entry.getKey(),
                                entry.getValue()))
                )
                .toList();
    }

}

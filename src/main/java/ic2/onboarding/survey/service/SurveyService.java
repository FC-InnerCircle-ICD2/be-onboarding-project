package ic2.onboarding.survey.service;

import ic2.onboarding.survey.dto.SurveyCreation;
import ic2.onboarding.survey.dto.SurveyInfo;
import ic2.onboarding.survey.entity.Survey;
import ic2.onboarding.survey.global.BizException;
import ic2.onboarding.survey.global.ErrorCode;
import ic2.onboarding.survey.service.out.SurveyStorage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyService {

    private final SurveyStorage surveyStorage;


    @Transactional
    public SurveyCreation createSurvey(@Valid SurveyCreation request) {

        SurveyInfo info = request.getInfo();

        // 선택 타입일 때, 선택 가능 항목이 비어있는지 확인
        info.getQuestions()
            .stream()
            .filter(question -> question.getInputType().isChoiceType())
            .filter(question -> question.getChoiceOptions().isEmpty())
            .findFirst()
            .ifPresent(question -> {
                throw new BizException(ErrorCode.CHOICE_TYPE_NEEDS_OPTIONS);
            });

        Survey saved = surveyStorage.save(
                Survey.builder()
                        .surveyInfo(info)
                        .build()
        );

        request.setUuid(saved.getUuid());
        return request;
    }


    @Transactional
    public SurveyInfo updateSurvey(String uuid, @Valid SurveyInfo request) {

        // 기존 설문 존재하는지 확인
        Survey survey = surveyStorage.findByUuid(uuid)
                .orElseThrow(() -> new BizException(ErrorCode.NOT_FOUND));

        // 변경 확인
        SurveyInfo currentSurveyInfo = survey.getSurveyInfo();
        if (Objects.equals(currentSurveyInfo, request)) {
            return currentSurveyInfo;
        }

        survey.setSurveyInfo(request);
        return request;
    }

}

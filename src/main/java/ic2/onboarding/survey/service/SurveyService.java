package ic2.onboarding.survey.service;

import ic2.onboarding.survey.dto.*;
import ic2.onboarding.survey.global.BizException;
import ic2.onboarding.survey.global.ErrorCode;
import ic2.onboarding.survey.repository.SurveyRepository;
import ic2.onboarding.survey.entity.Survey;
import ic2.onboarding.survey.entity.SurveyItem;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyService {

    private final SurveyRepository surveyRepository;


    @Transactional
    public SurveyCreationResponse createSurvey(@Valid SurveyCreationRequest request) {

        // 기본 정보 -> Survey Entity
        Survey survey = new Survey(request.basic().name(), request.basic().description());

        // 항목 정보 -> SurveyItem Entity List
        List<SurveyItem> surveyItems = request.items()
                .stream()
                .map(this::mapSurveyItem)
                .toList();

        // 저장
        survey.addAllItems(surveyItems);
        Survey saved = surveyRepository.save(survey);

        return SurveyCreationResponse.fromEntity(saved);
    }


    @Transactional
    public SurveyUpdateResponse updateSurvey(Long id, @Valid SurveyUpdateRequest request) {

        // 기존 설문 찾기
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new BizException(ErrorCode.NOT_FOUND));

        // 설문 기본정보 및 항목 정보 변경
        survey.update(
                request.newSurveyName(),
                request.newSurveyDescription(),
                request.items().stream()
                        .map(this::mapSurveyItem)
                        .toList()
        );

        surveyRepository.flush();
        return SurveyUpdateResponse.fromEntity(survey);
    }


    private SurveyItem mapSurveyItem(SurveyFormItem formItem) {

        return new SurveyItem(
                formItem.id(),
                null,
                formItem.name(),
                formItem.description(),
                formItem.inputTypeAsEnum(),
                formItem.required(),
                formItem.choicesAsString());
    }

}

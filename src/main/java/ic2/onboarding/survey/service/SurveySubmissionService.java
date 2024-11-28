package ic2.onboarding.survey.service;

import ic2.onboarding.survey.dto.*;
import ic2.onboarding.survey.entity.Survey;
import ic2.onboarding.survey.entity.SurveyItem;
import ic2.onboarding.survey.entity.SurveySubmissionItem;
import ic2.onboarding.survey.global.BizException;
import ic2.onboarding.survey.global.ErrorCode;
import ic2.onboarding.survey.repository.SurveyRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveySubmissionService {

    private final SurveyRepository surveyRepository;


    @Transactional
    public SurveySubmissionResponse submitSurvey(Long id, @Valid SurveySubmissionRequest request) {

        // 대상 설문 조회
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new BizException(ErrorCode.NOT_FOUND));

        // Entity 변환
        List<SurveySubmissionItem> submissionItems = request.items()
                .stream()
                .map(toBeSubmitted -> {

                    // 제출한 ITEM ID에 해당하는 SurveyItem 찾는다.
                    SurveyItem refSurveyItem = survey.getItems().stream()
                            .filter(stored -> Objects.equals(stored.getId(), toBeSubmitted.itemId()))
                            .findAny()
                            .orElseThrow(() -> new BizException(ErrorCode.NOT_VALIDATED));

                    return this.mapSurveySubmissionItem(toBeSubmitted, refSurveyItem);
                })
                .toList();

        // 제출
        survey.submitForm(submissionItems);
        surveyRepository.flush();
        return SurveySubmissionResponse.fromEntity(submissionItems);
    }


    private SurveySubmissionItem mapSurveySubmissionItem(SurveyFormSubmissionItem submissionItem,
                                                         SurveyItem surveyItem) {

        return new SurveySubmissionItem(
                null,
                null,
                surveyItem,
                surveyItem.getName(),
                submissionItem.answer());
    }

}

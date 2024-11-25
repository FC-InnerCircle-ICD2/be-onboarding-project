package ic2.onboarding.survey.service;

import ic2.onboarding.survey.repository.SurveyRepository;
import ic2.onboarding.survey.global.ItemInputType;
import ic2.onboarding.survey.dto.SurveyCreationRequest;
import ic2.onboarding.survey.dto.SurveyCreationResponse;
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

        // 항목 정보 -> SurveyItem Entity
        List<SurveyItem> surveyItems = request.items()
                .stream()
                .map(formItem -> new SurveyItem(
                        survey,
                        formItem.name(),
                        formItem.description(),
                        ItemInputType.fromString(formItem.inputType()),
                        formItem.required(),
                        formItem.choicesAsString()))
                .toList();

        // 저장
        survey.addAllItems(surveyItems);
        Survey saved = surveyRepository.save(survey);

        return SurveyCreationResponse.fromEntity(saved);
    }

}

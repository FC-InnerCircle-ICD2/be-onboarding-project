package ic2.onboarding.survey.api;

import ic2.onboarding.survey.global.ApiResult;
import ic2.onboarding.survey.dto.SurveyCreationRequest;
import ic2.onboarding.survey.dto.SurveyCreationResponse;
import ic2.onboarding.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/surveys")
public class SurveyController implements SurveyControllerDoc {

    private final SurveyService surveyService;


    @Override
    @PostMapping
    public ResponseEntity<ApiResult<SurveyCreationResponse>> createSurvey(@RequestBody SurveyCreationRequest surveyCreationRequest) {

        SurveyCreationResponse response = surveyService.createSurvey(surveyCreationRequest);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

}

package ic2.onboarding.survey.api;

import ic2.onboarding.survey.dto.SurveySubmissionRequest;
import ic2.onboarding.survey.dto.SurveySubmissionResponse;
import ic2.onboarding.survey.global.ApiResult;
import ic2.onboarding.survey.service.SurveySubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/surveys/{id}")
public class SurveySubmissionController implements SurveySubmissionControllerDoc {

    private final SurveySubmissionService surveySubmissionService;


    @Override
    @PostMapping("/submissions")
    public ResponseEntity<ApiResult<SurveySubmissionResponse>> submitSurvey(@PathVariable(name = "id") Long id,
                                                                            @RequestBody SurveySubmissionRequest request) {

        SurveySubmissionResponse response = surveySubmissionService.submitSurvey(id, request);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

}

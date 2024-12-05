package ic2.onboarding.survey.api;

import ic2.onboarding.survey.dto.SurveyCreation;
import ic2.onboarding.survey.dto.SurveyInfo;
import ic2.onboarding.survey.global.ApiResult;
import ic2.onboarding.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<ApiResult<SurveyCreation>> createSurvey(@RequestBody SurveyCreation request) {

        SurveyCreation response = surveyService.createSurvey(request);
        return ResponseEntity.ok(new ApiResult<>(response));
    }


    @Override
    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResult<SurveyInfo>> updateSurvey(@PathVariable(name = "uuid") String uuid,
                                                              @RequestBody SurveyInfo request) {

        SurveyInfo response = surveyService.updateSurvey(uuid, request);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

}

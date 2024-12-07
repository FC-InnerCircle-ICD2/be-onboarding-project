package ic2.onboarding.survey.api;

import ic2.onboarding.survey.dto.AnswerInfo;
import ic2.onboarding.survey.dto.SurveyAnswer;
import ic2.onboarding.survey.global.ApiResult;
import ic2.onboarding.survey.service.SurveySubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/surveys/{uuid}")
public class SurveySubmissionController implements SurveySubmissionControllerDoc {

    private final SurveySubmissionService surveySubmissionService;


    @Override
    @PostMapping("/submissions")
    public ResponseEntity<ApiResult<SurveyAnswer>> submitSurvey(@PathVariable(name = "uuid") String uuid,
                                                                @RequestBody SurveyAnswer request) {

        SurveyAnswer response = surveySubmissionService.submitSurvey(uuid, request);
        return ResponseEntity.ok(new ApiResult<>(response));
    }


    @Override
    @GetMapping
    public ResponseEntity<ApiResult<List<AnswerInfo>>> retrieveSurveySubmissions(@PathVariable(name = "uuid") String uuid,
                                                                                 @RequestParam(required = false, name = "questionName") String questionName,
                                                                                 @RequestParam(required = false, name = "answer") String answer) {

        List<AnswerInfo> response = surveySubmissionService.retrieveSurveySubmissions(uuid, questionName, answer);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

}

package net.gentledot.survey.controller;

import net.gentledot.survey.dto.common.ServiceResponse;
import net.gentledot.survey.dto.request.SearchSurveyAnswerRequest;
import net.gentledot.survey.dto.request.SubmitSurveyAnswer;
import net.gentledot.survey.dto.request.SurveyCreateRequest;
import net.gentledot.survey.dto.request.SurveyUpdateRequest;
import net.gentledot.survey.dto.response.SearchSurveyAnswerResponse;
import net.gentledot.survey.dto.response.SurveyCreateResponse;
import net.gentledot.survey.dto.response.SurveyUpdateResponse;
import net.gentledot.survey.service.SurveyAnswerService;
import net.gentledot.survey.service.SurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("v1/survey")
@RestController
public class SurveyController {
    private final SurveyService surveyService;
    private final SurveyAnswerService surveyAnswerService;

    public SurveyController(SurveyService surveyService, SurveyAnswerService surveyAnswerService) {
        this.surveyService = surveyService;
        this.surveyAnswerService = surveyAnswerService;
    }

    @PostMapping
    public ResponseEntity<ServiceResponse<SurveyCreateResponse>> createSurvey(
            @RequestBody SurveyCreateRequest surveyRequest) {
        SurveyCreateResponse createResult = surveyService.createSurvey(surveyRequest);
        return ResponseEntity.ok(ServiceResponse.success(createResult));
    }

    @PutMapping
    public ResponseEntity<ServiceResponse<SurveyUpdateResponse>> updateSurvey(
            @RequestBody SurveyUpdateRequest surveyRequest) {
        SurveyUpdateResponse updateResult = surveyService.updateSurvey(surveyRequest);
        return ResponseEntity.ok(ServiceResponse.success(updateResult));
    }

    @PostMapping("/{surveyId}/answer")
    public ResponseEntity<ServiceResponse<Void>> submitSurveyAnswer(@PathVariable("surveyId") String surveyId,
                                                                    @RequestBody List<SubmitSurveyAnswer> answer) {
        surveyAnswerService.submitSurveyAnswer(surveyId, answer);
        return ResponseEntity.ok(ServiceResponse.success(null));
    }

    @GetMapping("/{surveyId}/answer/all")
    public ResponseEntity<ServiceResponse<SearchSurveyAnswerResponse>> getAllSurveyAnswers(@PathVariable("surveyId") String surveyId,
                                                                                           @RequestBody SearchSurveyAnswerRequest request) {
        SearchSurveyAnswerResponse surveyAnswers = surveyAnswerService.getSurveyAnswers(request);
        return ResponseEntity.ok(ServiceResponse.success(surveyAnswers));
    }
}

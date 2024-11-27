package net.gentledot.survey.controller;

import net.gentledot.survey.dto.common.ServiceResponse;
import net.gentledot.survey.dto.request.SurveyCreateRequest;
import net.gentledot.survey.dto.request.SurveyUpdateRequest;
import net.gentledot.survey.dto.response.SurveyCreateResponse;
import net.gentledot.survey.dto.response.SurveyUpdateResponse;
import net.gentledot.survey.service.SurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("v1/survey")
@RestController
public class SurveyController {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public ResponseEntity<ServiceResponse<SurveyCreateResponse>> createSurvey(SurveyCreateRequest surveyRequest) {
        return ResponseEntity.ok(ServiceResponse.success(surveyService.createSurvey(surveyRequest)));
    }

    @PutMapping
    public ResponseEntity<ServiceResponse<SurveyUpdateResponse>> updateSurvey(SurveyUpdateRequest surveyRequest) {
        return ResponseEntity.ok(ServiceResponse.success(surveyService.updateSurvey(surveyRequest)));
    }
}

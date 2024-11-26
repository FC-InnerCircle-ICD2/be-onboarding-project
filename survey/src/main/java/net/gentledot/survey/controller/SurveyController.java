package net.gentledot.survey.controller;

import net.gentledot.survey.dto.common.ServiceResponse;
import net.gentledot.survey.dto.request.SurveyCreateRequest;
import net.gentledot.survey.dto.response.SurveyCreateResponse;
import net.gentledot.survey.service.SurveyService;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ServiceResponse<SurveyCreateResponse> createSurvey(SurveyCreateRequest surveyRequest) {
        return ServiceResponse.success(surveyService.createSurvey(surveyRequest));
    }
}

package org.survey.api.domain.survey.controller;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.survey.api.common.api.Api;
import org.survey.api.domain.survey.business.SurveyBusiness;
import org.survey.api.domain.survey.controller.model.SurveyBaseRequest;
import org.survey.api.domain.survey.controller.model.SurveyBaseResponse;
@RequiredArgsConstructor
@RestController
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyBusiness surveyBusiness;

    @PostMapping("/register")
    public Api<SurveyBaseResponse> register(
            @Valid
            @RequestBody Api<SurveyBaseRequest> request
    ){
        var response = surveyBusiness.register(request.getBody());
        return Api.OK(response);
    }
}

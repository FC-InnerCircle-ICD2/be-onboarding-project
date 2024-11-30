package org.survey.api.domain.survey.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.survey.api.common.api.Api;
import org.survey.api.domain.survey.business.SurveyBusiness;
import org.survey.api.domain.survey.controller.model.SurveyBaseRequest;
import org.survey.api.domain.survey.controller.model.SurveyBaseResponse;
import org.survey.api.domain.survey.controller.model.SurveyListResponse;

import java.util.List;

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

    @PostMapping("/update")
    public Api<SurveyBaseResponse> update(
            @Valid
            @RequestBody Api<SurveyBaseRequest> request
    ){
        var response = surveyBusiness.updateAll(request.getBody());
        return Api.OK(response);
    }

    @PostMapping("/delete")
    public Api<SurveyListResponse> delete(
            @Valid
            @RequestBody Api<Long> request
    ){
        var response = surveyBusiness.deleteSurvey(request.getBody());
        return Api.OK(response);
    }

    @PostMapping("/find/survey")
    public Api<SurveyBaseResponse> find(
            @Valid
            @RequestBody Api<Long> id
    ){
        var response = surveyBusiness.find(id.getBody());
        return Api.OK(response);
    }

    @GetMapping("/find/all")
    public Api<List<SurveyListResponse>> baseAllFind(){
        var response = surveyBusiness.baseAllFind();
        return Api.OK(response);
    }
}

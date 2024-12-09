package org.survey.domain.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.survey.common.api.Api;
import org.survey.domain.service.SurveyBusiness;
import org.survey.domain.controller.model.*;

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

    @PostMapping("/reply")
    public Api<SurveyReplyListResponse> reply(
            @Valid
            @RequestBody Api<SurveyReplyListRequest> request
    ){
        var response = surveyBusiness.reply(request.getBody());
        return Api.OK(response);
    }

    @PostMapping("/find")
    public Api<SurveyBaseResponse> allSurveyFind(
            @Valid
            @RequestBody Api<Long> id
    ){
        var response = surveyBusiness.find(id.getBody());
        return Api.OK(response);
    }

    @PostMapping("/find/replyAll")
    public Api<SurveyReplyListResponse> allReplyFind(
            @Valid
            @RequestBody Api<Long> id
    ){
        var response = surveyBusiness.replyAllFind(id.getBody());
        return Api.OK(response);
    }

    @GetMapping("/find/baseAll")
    public Api<List<SurveyListResponse>> baseAllFind(){
        var response = surveyBusiness.baseAllFind();
        return Api.OK(response);
    }

    @PostMapping("/find/reply/ByItemOrContent")
    public Api<SurveyBaseResponse> specialFind(
            @Valid
            @RequestBody Api<SurveySearchRequest> surveySearchRequestApi
    ){
        var response = surveyBusiness.replyFindByItemAndContent(surveySearchRequestApi.getBody());
        return Api.OK(response);
    }
}

package com.innercicle.adapter.out.service.v1.dto;

import com.innercicle.utils.ApiUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "survey-maker-api", url = "${service.dooray.url}")
public interface SurveyMakerService {

    @GetMapping("/api/v1/survey/{surveyId}")
    ApiUtil.ApiResult<Survey> getSurvey(@PathVariable("surveyId") Long surveyId);

}

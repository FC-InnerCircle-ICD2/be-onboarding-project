package com.icd.survey.api.controller.survey;

import com.icd.survey.api.dto.survey.request.CreateSurveyRequest;
import com.icd.survey.api.dto.survey.request.UpdateSurveyUpdateRequest;
import com.icd.survey.api.service.survey.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    /*
     * 디렉토리 구조나 아키텍처, 디자인 패턴 등 전반적인 리뷰를 듣고 싶습니다.
     * */
    @PostMapping
    public void createSurvey(@Validated @RequestBody CreateSurveyRequest requestDto) {
        surveyService.createSurvey(requestDto);
    }

    @PatchMapping
    public void updateSurvey(@Validated @RequestBody UpdateSurveyUpdateRequest requestDto) {
        surveyService.updateSurvey(requestDto);
    }

}

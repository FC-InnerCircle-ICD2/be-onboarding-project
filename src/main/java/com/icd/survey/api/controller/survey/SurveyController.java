package com.icd.survey.api.controller.survey;

import com.icd.survey.api.controller.survey.request.CreateSurveyRequest;
import com.icd.survey.api.controller.survey.request.UpdateSurveyUpdateRequest;
import com.icd.survey.api.controller.survey.request.converter.SurveyRequestConverter;
import com.icd.survey.api.entity.survey.dto.SurveyDto;
import com.icd.survey.api.service.survey.SurveyService;
import com.icd.survey.common.response.ApiResponse;
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


    @PostMapping
    public void createSurvey(@Validated @RequestBody CreateSurveyRequest requestDto) {
        surveyService.createSurvey(SurveyRequestConverter.createSurveyRequestConvert(requestDto));
    }

    @PatchMapping
    public void updateSurvey(@Validated @RequestBody UpdateSurveyUpdateRequest requestDto) {
        surveyService.updateSurvey(requestDto);
    }

    @GetMapping("/{surveySeq}")
    public ApiResponse<SurveyDto> getSurveyAnswer(@PathVariable Long surveySeq) {
        return new ApiResponse<>(surveyService.getSurveyAnswer(surveySeq));
    }

}

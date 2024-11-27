package com.icd.survey.api.controller.survey;

import com.icd.survey.api.dto.survey.request.CreateSurveyRequest;
import com.icd.survey.api.dto.survey.request.UpdateSurveyUpdateRequest;
import com.icd.survey.api.entity.dto.SurveyDto;
import com.icd.survey.api.service.survey.SurveyService;
import com.icd.survey.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;


    @GetMapping("/{surveySeq}")
    public ResponseEntity<ApiResponse<SurveyDto>> searchSurvey(@PathVariable Long surveySeq) {
        return ResponseEntity.ok(new ApiResponse<>(surveyService.getSurvey(surveySeq)));
    }

    @PostMapping
    public void createSurvey(@Validated @RequestBody CreateSurveyRequest requestDto) {
        surveyService.createSurvey(requestDto);
    }

    @PatchMapping
    public void updateSurvey(@Validated @RequestBody UpdateSurveyUpdateRequest requestDto) {
        surveyService.updateSurvey(requestDto);
    }

}

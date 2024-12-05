package com.onboarding.beonboardingproject.controller;

import com.onboarding.beonboardingproject.common.exception.InvalidException;
import com.onboarding.beonboardingproject.common.global.ApiResult;
import com.onboarding.beonboardingproject.common.global.ResponseCode;
import com.onboarding.beonboardingproject.dto.ResponseDto;
import com.onboarding.beonboardingproject.dto.SurveyDto;
import com.onboarding.beonboardingproject.dto.SurveyResponseDto;
import com.onboarding.beonboardingproject.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    // 설문 조사 생성
    @PostMapping
    public ResponseEntity<ApiResult<String>> createSurvey(@RequestBody SurveyDto surveyDto) {
        SurveyResponseDto surveyResponseDto = surveyService.createSurvey(surveyDto);

        ApiResult apiResult = ApiResult.success("설문조사가 생성되었습니다. [Survey ID: " + surveyResponseDto.getSurveyId() + "]");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResult);
    }

    // 설문 조사 수정
    @PutMapping("/{surveyId}")
    public ResponseEntity<ApiResult<SurveyResponseDto>> updateSurvey(@PathVariable Long surveyId, @RequestBody SurveyDto surveyDto) {
        ApiResult apiResult;

        try {
            SurveyResponseDto surveyResponseDto = surveyService.updateSurvey(surveyId, surveyDto);
            apiResult = new ApiResult(ResponseCode.SUCCESS, surveyResponseDto);
        }catch (InvalidException e){
            apiResult = ApiResult.failure("설문조사 수정 실패하였습니다."+e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResult);
    }
}

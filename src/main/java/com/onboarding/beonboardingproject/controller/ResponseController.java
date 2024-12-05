package com.onboarding.beonboardingproject.controller;

import com.onboarding.beonboardingproject.common.exception.InvalidException;
import com.onboarding.beonboardingproject.common.global.ApiResult;
import com.onboarding.beonboardingproject.common.global.ResponseCode;
import com.onboarding.beonboardingproject.dto.ResponseDto;
import com.onboarding.beonboardingproject.dto.SurveyResponseDto;
import com.onboarding.beonboardingproject.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/response")
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService responseService;

    // 설문 응답 제출
    @PostMapping
    public ResponseEntity<ApiResult<String>> submitResponse(@RequestBody ResponseDto responseDto) {
        ApiResult<String> apiResult;
        try{
            responseService.submitResponse(responseDto);
            apiResult = ApiResult.success("설문조사 응답이 성공적으로 제출되었습니다.");
        } catch (InvalidException e){
            apiResult = ApiResult.failure("설문조사 응답 제출 실패하였습니다."+e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResult);
    }

    // 설문 응답 조회
    @GetMapping("/{surveyId}")
    public ResponseEntity<ApiResult<ResponseDto>> getResponses(@PathVariable Long surveyId) {
        ApiResult<ResponseDto> apiResult;

        try{
            List<ResponseDto> responses = responseService.getResponses(surveyId);
            if (responses.isEmpty()) {
                apiResult = new ApiResult(ResponseCode.SUCCESS,"응답 값이 존재하지 않습니다.");
                return ResponseEntity.status(HttpStatus.CREATED).body(apiResult);
            }
            apiResult = new ApiResult(ResponseCode.SUCCESS,responses);
        }catch (InvalidException e){
            apiResult = ApiResult.failure(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResult);
    }
}

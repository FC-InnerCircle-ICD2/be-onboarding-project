package com.innercircle.surveryproject.modules.controller;

import com.innercircle.surveryproject.modules.dto.SurveyAnswerCreateDto;
import com.innercircle.surveryproject.modules.dto.SurveyAnswerDto;
import com.innercircle.surveryproject.modules.dto.SurveyAnswerKeywordDto;
import com.innercircle.surveryproject.modules.dto.SurveyAnswerResponseDto;
import com.innercircle.surveryproject.modules.global.ResponseUtils;
import com.innercircle.surveryproject.modules.service.SurveyAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 설문조사 응답 api
 */
@Tag(name = "설문조사 응답 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/surveyAnswer")
public class SurveyAnswerController {

    private final SurveyAnswerService surveyAnswerService;

    @Operation(summary = "설문조사 응답 제출")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "설문조사 응답 제출에 성공하였습니다."),
        @ApiResponse(responseCode = "400", description = "설문조사 응답 제출에 실패하였습니다.")
    })
    @PostMapping
    public ResponseEntity createSurveyAnswer(@RequestBody SurveyAnswerCreateDto surveyAnswerCreateDto) {

        SurveyAnswerDto surveyAnswerDto = surveyAnswerService.createSurveyAnswer(surveyAnswerCreateDto);

        return ResponseUtils.created(surveyAnswerDto, "설문조사 응답이 완료되었습니다.");
    }

    @Operation(summary = "설문조사 응답 조회 조건")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "설문조사 응답 키워드 조회에 성공하였습니다."),
        @ApiResponse(responseCode = "400", description = "설문조사 응답 키워드 조회에 실패하였습니다.")
    })
    @GetMapping("/{surveyAnswerId}/keywords")
    public ResponseEntity retrieveSurveyAnswerKeyword(@PathVariable Long surveyAnswerId) {

        List<SurveyAnswerKeywordDto> surveyAnswerKeywordDtoList = surveyAnswerService.retrieveSurveyAnswerKeywords(surveyAnswerId);

        return ResponseUtils.ok(surveyAnswerKeywordDtoList, "설문조사 응답 키워드 조회에 성공하였습니다.");
    }

    @Operation(summary = "설문조사 응답 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "설문조사 응답 조회에 성공하였습니다."),
        @ApiResponse(responseCode = "400", description = "설문조사 응답 조회에 실패하였습니다.")
    })
    @GetMapping("/{surveyAnswerId}")
    public ResponseEntity retrieveSurveyAnswer(@PathVariable Long surveyAnswerId,
                                               @RequestParam(required = false) Long surveyItemId,
                                               @RequestParam(required = false) String surveyItemContent) {

        List<SurveyAnswerResponseDto> surveyAnswerDto =
            surveyAnswerService.retrieveSurveyAnswer(surveyAnswerId, surveyItemId, surveyItemContent);

        return ResponseUtils.ok(surveyAnswerDto, "설문조사 응답 조회에 성공하였습니다.");
    }

}

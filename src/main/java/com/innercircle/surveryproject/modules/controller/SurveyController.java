package com.innercircle.surveryproject.modules.controller;

import com.innercircle.surveryproject.modules.dto.ResponseUtils;
import com.innercircle.surveryproject.modules.dto.SurveyCreateDto;
import com.innercircle.surveryproject.modules.dto.SurveyDto;
import com.innercircle.surveryproject.modules.dto.SurveyUpdateDto;
import com.innercircle.surveryproject.modules.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 설문조사 API
 */
@Tag(name = "설문조사 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;

    /**
     * 설문조사 등록 API
     *
     * @return
     */
    @Operation(summary = "설문조사 등록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "설문조사 등록에 성공하였습니다."),
        @ApiResponse(responseCode = "400", description = "설문조사 등록에 실패하였습니다.")
    })
    @PostMapping
    public ResponseEntity createSurvey(@RequestBody @Validated SurveyCreateDto surveyCreateDto) {

        SurveyDto surveyDto = surveyService.createSurvey(surveyCreateDto);

        return ResponseUtils.created(surveyDto, "설문조사 등록에 성공하였습니다.");
    }

    /**
     * 설문조사 수정 API
     *
     * @param surveyUpdateDto
     * @return
     */
    @Operation(summary = "설문조사 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "설문조사 수정에 성공하였습니다."),
        @ApiResponse(responseCode = "400", description = "설문조사 수정에 실패하였습니다.")
    })
    @PutMapping
    public ResponseEntity updateSurvey(@RequestBody @Validated SurveyUpdateDto surveyUpdateDto) {

        SurveyDto surveyDto = surveyService.updateSurvey(surveyUpdateDto);

        return ResponseUtils.ok(surveyDto, "설문조사 수정에 성공하였습니다.");
    }

    @Operation(summary = "설문조사 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "설문조사 조회에 성공하였습니다."),
        @ApiResponse(responseCode = "400", description = "설문조사 조회에 실패하였습니다.")
    })
    @GetMapping("/{surveyId}")
    public ResponseEntity retrieveSurvey(@PathVariable Long surveyId) {

        SurveyDto retrieveSurveyDto = surveyService.retrieveSurvey(surveyId);

        return ResponseUtils.ok(retrieveSurveyDto, "설문조사 조회에 성공하였습니다.");
    }

}

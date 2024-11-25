package com.innercircle.surveryproject.modules.controller;

import com.innercircle.surveryproject.modules.dto.ResponseUtils;
import com.innercircle.surveryproject.modules.dto.SurveyCreateDto;
import com.innercircle.surveryproject.modules.dto.SurveyDto;
import com.innercircle.surveryproject.modules.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Operation(summary = "Get example data", description = "Returns example data for demonstration purposes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공하였습니다."),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping
    public ResponseEntity createSurvey(@RequestBody @Validated SurveyCreateDto surveyCreateDto) {

        SurveyDto surveyDto = surveyService.createSurvey(surveyCreateDto);

        return ResponseUtils.created(surveyDto, "성공하였습니다.");
    }

}

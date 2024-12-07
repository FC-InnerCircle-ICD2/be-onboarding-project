package com.survey.www.surveys.controller;

import com.survey.www.commons.config.validation.ValidationSequence;
import com.survey.www.commons.response.CommonResponse;
import com.survey.www.surveys.dto.request.SurveyAnswerCreateRequest;
import com.survey.www.surveys.dto.request.SurveyAnswerDetailRequest;
import com.survey.www.surveys.dto.request.SurveyCreateRequest;
import com.survey.www.surveys.dto.request.SurveyUpdateRequest;
import com.survey.www.surveys.dto.response.*;
import com.survey.www.surveys.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/surveys")
@RestController
public class SurveyApiController implements SurveyApi {
    private final SurveyService surveyService;

    @PostMapping
    public ResponseEntity<CommonResponse<SurveyCreateResponse>> createQuestion(@RequestBody @Validated(ValidationSequence.class) SurveyCreateRequest surveyCreateRequest) {
        return ResponseEntity.ok(new CommonResponse<>(surveyService.createQuestion(surveyCreateRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<SurveyQuestionDetailResponse>> detailQuestion(@PathVariable("id") Long surveyId) {
        return ResponseEntity.ok(new CommonResponse<>(surveyService.detailQuestion(surveyId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<SurveyUpdateResponse>> update(@PathVariable("id") Long surveyId, @RequestBody @Validated(ValidationSequence.class) SurveyUpdateRequest surveyUpdateRequest) {
        return ResponseEntity.ok(new CommonResponse<>(surveyService.update(surveyId, surveyUpdateRequest)));
    }

    @PostMapping("/{id}/answers")
    public ResponseEntity<CommonResponse<SurveyAnswerCreateResponse>> createAnswer(@PathVariable("id") Long surveyId, @RequestBody @Validated(ValidationSequence.class) SurveyAnswerCreateRequest surveyAnswerCreateRequest) {
        return ResponseEntity.ok(new CommonResponse<>(surveyService.createAnswer(surveyId, surveyAnswerCreateRequest)));
    }

    @GetMapping("/{id}/answers")
    public ResponseEntity<CommonResponse<List<SurveyAnswerDetailResponse>>> detailAnswer(@PathVariable("id") Long surveyId, @RequestBody SurveyAnswerDetailRequest surveyAnswerDetailRequest) {
        return ResponseEntity.ok(new CommonResponse<>(surveyService.detailAnswer(surveyId, surveyAnswerDetailRequest)));
    }
}

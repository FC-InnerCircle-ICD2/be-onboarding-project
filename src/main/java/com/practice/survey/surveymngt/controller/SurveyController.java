package com.practice.survey.surveymngt.controller;

import com.practice.survey.common.response.ResponseTemplate;
import com.practice.survey.common.response.StatusEnum;
import com.practice.survey.surveymngt.model.dto.SurveyRequestDto;
import com.practice.survey.surveymngt.model.dto.SurveyResponseDto;
import com.practice.survey.surveymngt.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/v1/survey")
@RequiredArgsConstructor
@Tag(name = "Survey Management", description = "설문조사 관리 API")
public class SurveyController implements SurveyControllerDocs {

    private final SurveyService surveyService;

    @Override
    public ResponseTemplate<StatusEnum> createSurvey(@Valid @RequestBody SurveyRequestDto surveyRequestDto) {
        return surveyService.createSurvey(surveyRequestDto);
    }

    @Override
    public ResponseTemplate<StatusEnum> updateSurvey(@Valid @RequestBody SurveyRequestDto surveyRequestDto) {
        return surveyService.updateSurvey(surveyRequestDto);
    }

    @Override
    public ResponseTemplate<List<SurveyResponseDto>> getSurveyResponse(Long surveyId,String itemName,String responseValue){
        return surveyService.getSurveyResponse(surveyId, itemName, responseValue);
    }
}

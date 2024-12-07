package com.fc.survey.controller;

import com.fc.survey.constant.ResponseConstants;
import com.fc.survey.dto.ResponseApi;
import com.fc.survey.dto.request.SurveyModDTO;
import com.fc.survey.dto.request.SurveyRegDTO;
import com.fc.survey.dto.response.SurveyModResponse;
import com.fc.survey.dto.response.SurveyRegResponse;
import com.fc.survey.entity.Survey;
import com.fc.survey.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
@Tag(name = "설문")
public class SurveyController {

  private final SurveyService surveyService;

  @PostMapping
  @Operation(summary = "설문조사 생성", description = "설문조사를 생성할 때 호출하는 API")
  @ApiResponses(value = {
      @ApiResponse(responseCode = ResponseConstants.SURVEY_CREATED_CODE, description = ResponseConstants.SURVEY_CREATED_MESSAGE)
  })
  public ResponseApi<SurveyRegResponse> saveSurvey(@RequestBody SurveyRegDTO request){
    log.info("call saveSurvey: {}", request);
    Survey survey = surveyService.save(request);
    return new ResponseApi<>(
        ResponseConstants.SURVEY_CREATED_CODE,
        ResponseConstants.SURVEY_CREATED_MESSAGE,
        new SurveyRegResponse(survey.getId(),  survey.getName()));
  }

  @PutMapping
  @Operation(summary = "설문조사 수정", description = "등록된 설문조사를 수정할 때 호출하는 API")
  @ApiResponses(value = {
      @ApiResponse(responseCode = ResponseConstants.SURVEY_UPDATE_CODE, description = ResponseConstants.SURVEY_UPDATE_MESSAGE)
  })
  public ResponseApi<SurveyModResponse> updateSurvey(@RequestBody SurveyModDTO request){
    log.info("call updateSurvey: {}", request);
    Survey survey = surveyService.update(request);
    return new ResponseApi<>(
        ResponseConstants.SURVEY_UPDATE_CODE,
        ResponseConstants.SURVEY_UPDATE_MESSAGE,
        new SurveyModResponse(survey.getId(), survey.getName(), survey.getDescription(), survey.getQuestions())
    );
  }


}

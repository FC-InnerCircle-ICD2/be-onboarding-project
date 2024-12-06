package com.onboarding.api.web.survey.controller;

import com.onboarding.api.web.survey.dto.request.SurveyCreateReqDto;
import com.onboarding.api.web.survey.dto.request.SurveyUpdateReqDto;
import com.onboarding.core.global.dto.GlobalResponse;
import com.onboarding.survey.dto.response.SurveyWithQuestionsDTO;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.facade.SurveyFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "설문 생성", description = "설문 생성 관련 API입니다.")
@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyController {
  private final SurveyFacade surveyFacade;

  @Operation(
      summary = "설문 생성",
      description = "새로운 설문을 생성합니다.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "설문 생성 요청 객체",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = SurveyCreateReqDto.class))
      ),
      responses = {
          @ApiResponse(responseCode = "200", description = "설문 생성 성공", content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = GlobalResponse.class)))
      }
  )
  @PostMapping
  public ResponseEntity<?> createSurvey(@RequestBody SurveyCreateReqDto req) {
    surveyFacade.createSurvey(req.surveyObjectOf());
    return ResponseEntity.ok(GlobalResponse.success());
  }

  @Operation(
      summary = "설문 수정",
      description = "기존 설문을 수정합니다.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "설문 수정 요청 객체",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = SurveyUpdateReqDto.class))
      ),
      responses = {
          @ApiResponse(responseCode = "200", description = "설문 수정 성공", content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
          @ApiResponse(responseCode = "404", description = "설문을 찾을 수 없음", content = @Content(schema = @Schema(implementation = GlobalResponse.class)))
      }
  )
  @PatchMapping("/{surveyId}")
  public ResponseEntity<?> updateSurvey(@PathVariable Long surveyId, @RequestBody
      SurveyUpdateReqDto req) {
    surveyFacade.updateSurvey(surveyId, req.surveyObjectOf());
    return ResponseEntity.ok(GlobalResponse.success());
  }

  @Operation(
      summary = "설문 조회",
      description = "설문과 관련된 모든 질문을 조회합니다.",
      parameters = @Parameter(name = "surveyId", description = "조회할 설문의 ID", required = true),
      responses = {
          @ApiResponse(responseCode = "200", description = "설문 조회 성공", content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
          @ApiResponse(responseCode = "404", description = "설문을 찾을 수 없음", content = @Content(schema = @Schema(implementation = GlobalResponse.class)))
      }
  )
  @GetMapping("/{surveyId}")
  public ResponseEntity<GlobalResponse<SurveyWithQuestionsDTO>> getSurveyWithQuestions(@PathVariable Long surveyId) {
    SurveyWithQuestionsDTO surveyWithQuestions = surveyFacade.getSurveyByIdWithQuestions(surveyId);
    return ResponseEntity.ok(GlobalResponse.success(surveyWithQuestions));
  }



}

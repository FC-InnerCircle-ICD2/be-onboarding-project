package com.onboarding.api.web.response.controller;

import com.onboarding.api.web.response.dto.request.AnswerRequest;
import com.onboarding.api.web.response.dto.request.SubmitResponseRequest;
import com.onboarding.core.global.dto.GlobalResponse;
import com.onboarding.response.dto.response.ResponseDTO;
import com.onboarding.response.facade.ResponseFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "설문 응답", description = "설문 응답 관련 API입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ResponseController {
  private final ResponseFacade responseFacade;

  @PostMapping("/{surveyId}/responses")
  @Operation(
      summary = "응답 제출",
      description = "설문에 대한 응답을 제출합니다. 응답은 이메일과 질문에 대한 답변 리스트로 구성됩니다.",
      responses = {
          @ApiResponse(responseCode = "200", description = "응답 제출 성공",
              content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
          @ApiResponse(responseCode = "400", description = "유효하지 않은 요청",
              content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
          @ApiResponse(responseCode = "404", description = "설문을 찾을 수 없음",
              content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
      }
  )
  public ResponseEntity<?> submitResponse(
      @PathVariable Long surveyId,
      @RequestBody SubmitResponseRequest request
  ) {
    responseFacade.submitResponse(surveyId, request.of());
    return ResponseEntity.ok(GlobalResponse.success());
  }

  @GetMapping("/{surveyId}")
  @Operation(
      summary = "응답 조회",
      description = "특정 설문에 대한 모든 응답을 조회합니다.",
      responses = {
          @ApiResponse(responseCode = "200", description = "응답 조회 성공",
              content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
          @ApiResponse(responseCode = "404", description = "설문을 찾을 수 없음")
      }
  )
  public ResponseEntity<GlobalResponse<List<ResponseDTO>>> getAllResponses(@PathVariable Long surveyId) {
    List<ResponseDTO> responses = responseFacade.getAllResponses(surveyId);
    return ResponseEntity.ok(GlobalResponse.success(responses));
  }

  @GetMapping("/{surveyId}/search")
  @Operation(summary = "응답 검색", description = "설문 응답 검색")
  public ResponseEntity<GlobalResponse<List<ResponseDTO>>> searchResponses(@PathVariable Long surveyId,
      @RequestParam(required = false) String questionTitle,
      @RequestParam(required = false) String responseValue) {
    List<ResponseDTO> responses = responseFacade.searchResponses(surveyId, questionTitle, responseValue);
    return ResponseEntity.ok(GlobalResponse.success(responses));
  }
}

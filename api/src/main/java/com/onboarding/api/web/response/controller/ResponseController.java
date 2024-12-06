package com.onboarding.api.web.response.controller;

import com.onboarding.api.web.response.dto.request.AnswerRequest;
import com.onboarding.api.web.response.dto.request.SubmitResponseRequest;
import com.onboarding.core.global.dto.GlobalResponse;
import com.onboarding.response.dto.response.ResponseDTO;
import com.onboarding.response.facade.ResponseFacade;
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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ResponseController {
  private final ResponseFacade responseFacade;

  @PostMapping("/{surveyId}/responses")
  public ResponseEntity<?> submitResponse(
      @PathVariable Long surveyId,
      @RequestBody SubmitResponseRequest request
  ) {
    responseFacade.submitResponse(surveyId, request.of());
    return ResponseEntity.ok(GlobalResponse.success());
  }

  @GetMapping("/{surveyId}")
  public ResponseEntity<GlobalResponse<List<ResponseDTO>>> getAllResponses(@PathVariable Long surveyId) {
    List<ResponseDTO> responses = responseFacade.getAllResponses(surveyId);
    return ResponseEntity.ok(GlobalResponse.success(responses));
  }

  @GetMapping("/{surveyId}/search")
  public ResponseEntity<GlobalResponse<List<ResponseDTO>>> searchResponses(@PathVariable Long surveyId,
      @RequestParam(required = false) String questionTitle,
      @RequestParam(required = false) String responseValue) {
    List<ResponseDTO> responses = responseFacade.searchResponses(surveyId, questionTitle, responseValue);
    return ResponseEntity.ok(GlobalResponse.success(responses));
  }
}

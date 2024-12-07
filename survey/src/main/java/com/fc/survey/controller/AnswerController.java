package com.fc.survey.controller;

import com.fc.survey.constant.ResponseConstants;
import com.fc.survey.dto.ResponseApi;
import com.fc.survey.dto.request.SurveySubmitDTO;
import com.fc.survey.dto.response.SurveyResponse;
import com.fc.survey.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
@Tag(name = "Answer")
public class AnswerController {

  private final AnswerService answerService;

  @PostMapping
  @Operation(summary = "응답 제출", description = "사용자가 설문 작성 후 응답 제출 시 호출하는 API")
  @ApiResponses(value = {
      @ApiResponse(responseCode = ResponseConstants.ANSWER_SUBMIT_CODE, description = ResponseConstants.ANSWER_SUBMIT_MESSAGE)
  })
  public ResponseApi<Object> saveAnswer(@RequestBody SurveySubmitDTO request){
    log.info("call saveAnswer: {}", request);
    answerService.save(request);
    return new ResponseApi<>(
        ResponseConstants.ANSWER_SUBMIT_CODE,
        ResponseConstants.ANSWER_SUBMIT_MESSAGE,
        null);
  }

  @GetMapping("/{id}")
  @Operation(summary = "응답 조회", description = "사용자가 제출한 응답 조회 시 호출하는 API (embedded 작업 전)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = ResponseConstants.ANSWER_GET_CODE, description = ResponseConstants.ANSWER_GET__MESSAGE)
  })
  @Parameter(name="id", description = "응답 식별자 값", example = "1")
  public ResponseApi<List<SurveyResponse>> findAllAnswer(@PathVariable Long id){
    log.info("call findAllAnswer: {}", id);
    List<SurveyResponse> responses = answerService.findAllAnswer(id);
    return new ResponseApi<>(
        ResponseConstants.ANSWER_GET_CODE,
        ResponseConstants.ANSWER_GET__MESSAGE,
        responses
    );
  }
}

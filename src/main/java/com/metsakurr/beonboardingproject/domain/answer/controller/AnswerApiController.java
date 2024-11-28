package com.metsakurr.beonboardingproject.domain.answer.controller;

import com.metsakurr.beonboardingproject.common.dto.ApiResponse;
import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import com.metsakurr.beonboardingproject.domain.answer.dto.CreateAnswerRequest;
import com.metsakurr.beonboardingproject.domain.answer.dto.DetailAnswerResponse;
import com.metsakurr.beonboardingproject.domain.answer.entity.Response;
import com.metsakurr.beonboardingproject.domain.answer.service.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/answer")
public class AnswerApiController {
    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(
            @Valid @RequestBody CreateAnswerRequest createAnswerRequest
    ) {
        ApiResponse response = new ApiResponse<>(ResponseCode.SUCCESS, answerService.create(createAnswerRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DetailAnswerResponse>> detail(
        @Valid @PathVariable("id") long idx
    ) {
        DetailAnswerResponse detailAnswerResponse = answerService.detail(idx);
        ApiResponse response = new ApiResponse<>(ResponseCode.SUCCESS, detailAnswerResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

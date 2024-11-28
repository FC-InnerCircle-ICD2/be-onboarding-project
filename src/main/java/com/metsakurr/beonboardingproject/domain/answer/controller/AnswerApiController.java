package com.metsakurr.beonboardingproject.domain.answer.controller;

import com.metsakurr.beonboardingproject.common.dto.ApiResponse;
import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import com.metsakurr.beonboardingproject.domain.answer.dto.CreateAnswerRequest;
import com.metsakurr.beonboardingproject.domain.answer.service.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

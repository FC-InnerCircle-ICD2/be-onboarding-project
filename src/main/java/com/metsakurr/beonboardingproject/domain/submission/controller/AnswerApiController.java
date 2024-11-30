package com.metsakurr.beonboardingproject.domain.submission.controller;

import com.metsakurr.beonboardingproject.common.dto.ApiResponse;
import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import com.metsakurr.beonboardingproject.domain.submission.dto.SubmissionCreationRequest;
import com.metsakurr.beonboardingproject.domain.submission.dto.DetailAnswerResponse;
import com.metsakurr.beonboardingproject.domain.submission.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/submission")
public class AnswerApiController {
    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(
            @Valid @RequestBody SubmissionCreationRequest submissionCreationRequest
    ) {
        ApiResponse response = new ApiResponse<>(ResponseCode.SUCCESS, submissionService.create(submissionCreationRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DetailAnswerResponse>> detail(
        @Valid @PathVariable("id") long idx
    ) {
        DetailAnswerResponse detailAnswerResponse = submissionService.detail(idx);
        ApiResponse response = new ApiResponse<>(ResponseCode.SUCCESS, detailAnswerResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

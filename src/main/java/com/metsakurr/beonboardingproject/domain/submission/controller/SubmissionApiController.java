package com.metsakurr.beonboardingproject.domain.submission.controller;

import com.metsakurr.beonboardingproject.common.dto.ApiResponse;
import com.metsakurr.beonboardingproject.domain.submission.dto.SubmissionCreationRequest;
import com.metsakurr.beonboardingproject.domain.submission.dto.SubmissionCreationResponse;
import com.metsakurr.beonboardingproject.domain.submission.dto.SubmissionDetailResponse;
import com.metsakurr.beonboardingproject.domain.submission.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/submission")
public class SubmissionApiController {
    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubmissionCreationResponse>> create(
            @Valid @RequestBody SubmissionCreationRequest submissionCreationRequest
    ) {
        ApiResponse response = submissionService.create(submissionCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubmissionDetailResponse>> detail(
        @Valid @PathVariable("id") long idx
    ) {
        ApiResponse response = submissionService.detail(idx);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

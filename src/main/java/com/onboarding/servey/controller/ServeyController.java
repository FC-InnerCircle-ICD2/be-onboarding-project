package com.onboarding.servey.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onboarding.common.controller.ApiController;
import com.onboarding.servey.dto.request.OptionRequest;
import com.onboarding.servey.dto.request.QuestionRequest;
import com.onboarding.servey.dto.request.ServeyRequest;
import com.onboarding.servey.dto.response.ServeyResponse;
import com.onboarding.servey.service.ServeyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api("설문조사 API")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ServeyController extends ApiController {

	private final ServeyService serveyService;

	@ApiOperation(value = "단일 설문조사 조회", notes = "설문조사 ID로 설문조사를 조회합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "serveyId", value = "설문조사 ID", required = true, dataType = "long", paramType = "path")
	})
	@GetMapping(value = "/servey/{serveyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ServeyResponse> survey(@PathVariable @NotNull Long serveyId) {
		return ResponseEntity.ok(serveyService.getServey(serveyId));
	}

	@ApiOperation(value = "설문조사 생성", notes = "설문조사를 생성합니다.")
	@PostMapping(value = "/servey", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@Valid @RequestBody ServeyRequest serveyRequest) {
		serveyService.create(serveyRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@ApiOperation(value = "설문조사 수정", notes = "설문조사를 수정합니다.")
	@PatchMapping(value = "/servey/{serveyId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(
		@PathVariable @NotNull Long serveyId,
		@Valid @RequestBody ServeyRequest serveyRequest) {
		serveyService.update(serveyId, serveyRequest);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@ApiOperation(value = "설문받을 항목 수정", notes = "설문받을 항목을 수정합니다.")
	@PatchMapping(value = "/servey/{serveyId}/{questionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(
		@PathVariable @NotNull Long serveyId,
		@PathVariable @NotNull Long questionId,
		@Valid @RequestBody QuestionRequest questionRequest) {
		serveyService.update(serveyId, questionId, questionRequest);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@ApiOperation(value = "선택 할 수 있는 후보 수정", notes = "선택 할 수 있는 후보를 수정합니다.")
	@PatchMapping(value = "/servey/{serveyId}/{questionId}/{optionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(
		@PathVariable @NotNull Long serveyId,
		@PathVariable @NotNull Long questionId,
		@PathVariable @NotNull Long optionId,
		@Valid @RequestBody OptionRequest optionRequest) {
		serveyService.update(serveyId, questionId, optionId, optionRequest);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}

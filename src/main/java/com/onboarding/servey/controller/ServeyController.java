package com.onboarding.servey.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onboarding.common.controller.ApiController;
import com.onboarding.servey.dto.request.OptionRequest;
import com.onboarding.servey.dto.request.QuestionReplyRequest;
import com.onboarding.servey.dto.request.QuestionRequest;
import com.onboarding.servey.dto.request.ServeyRequest;
import com.onboarding.servey.dto.response.QuestionReplyResponse;
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
@Validated
public class ServeyController extends ApiController {

	private final ServeyService serveyService;

	@ApiOperation(value = "설문조사 응답 조회", notes = "설문 응답 항목의 이름과 응답 값을 기반으로 조회합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "query", value = "설문 받을 항목 이름 및 응답", dataType = "string", paramType = "query")
	})
	@GetMapping(value = "/servey", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuestionReplyResponse>> survey(
		@PageableDefault(sort = {"id"}) Pageable pageable,
		@RequestParam(required = false) String query) {
		return ResponseEntity.ok(serveyService.servey(pageable, query));
	}

	@ApiOperation(value = "설문조사 응답 제출", notes = "설문조사 응답을 제출합니다.")
	@PatchMapping(value = "/servey/{serveyId}/submit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> submit(
		@PathVariable @NotNull Long serveyId,
		@Valid @RequestBody List<QuestionReplyRequest> questionReplyRequest) {
		serveyService.submit(serveyId, questionReplyRequest);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@ApiOperation(value = "설문조사 조회", notes = "설문조사 응답을 조회합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "serveyId", value = "설문조사 ID", required = true, dataType = "long", paramType = "path")
	})
	@GetMapping(value = "/servey/{serveyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ServeyResponse> survey(@PathVariable @NotNull Long serveyId) {
		return ResponseEntity.ok(serveyService.servey(serveyId));
	}

	@ApiOperation(value = "설문조사 생성", notes = "설문조사를 생성합니다.")
	@PostMapping(value = "/servey", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@Valid @RequestBody ServeyRequest serveyRequest) {
		serveyService.create(serveyRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@ApiOperation(value = "설문받을 항목 추가", notes = "설문받을 항목을 추가합니다.")
	@PostMapping(value = "/servey/{serveyId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(
		@PathVariable @NotNull Long serveyId,
		@Valid @Size(min = 1, max = 10, message = "설문 받을 항목은 1개 ~ 10개까지 포함 할 수 있습니다.") @RequestBody List<QuestionRequest> questionRequests) {
		serveyService.create(serveyId, questionRequests);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@ApiOperation(value = "선택할 수 있는 후보 추가", notes = "선택할 수 있는 후보를 추가합니다.")
	@PostMapping(value = "/servey/{serveyId}/{questionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(
		@PathVariable @NotNull Long serveyId,
		@PathVariable @NotNull Long questionId,
		@Valid @RequestBody List<OptionRequest> optionRequests) {
		serveyService.create(serveyId, questionId, optionRequests);
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

	@ApiOperation(value = "설문조사 삭제", notes = "설문조사를 삭제합니다.")
	@DeleteMapping(value = "/servey/{serveyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(
		@PathVariable @NotNull Long serveyId) {
		serveyService.delete(serveyId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@ApiOperation(value = "설문받을 항목 삭제", notes = "설문받을 항목을 삭제합니다.")
	@DeleteMapping(value = "/servey/{serveyId}/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(
		@PathVariable @NotNull Long serveyId,
		@PathVariable @NotNull Long questionId) {
		serveyService.delete(serveyId, questionId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@ApiOperation(value = "선택 할 수 있는 후보 삭제", notes = "선택 할 수 있는 후보를 삭제합니다.")
	@DeleteMapping(value = "/servey/{serveyId}/{questionId}/{optionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(
		@PathVariable @NotNull Long serveyId,
		@PathVariable @NotNull Long questionId,
		@PathVariable @NotNull Long optionId) {
		serveyService.delete(serveyId, questionId, optionId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}

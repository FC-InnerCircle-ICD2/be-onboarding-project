package com.onboarding.servey.controller;

import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onboarding.common.controller.ApiController;
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
	@GetMapping(value = "/servey/{serveyId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServeyResponse getServey(@PathVariable @NotNull Long serveyId) {
		return serveyService.getServey(serveyId);
	}
}

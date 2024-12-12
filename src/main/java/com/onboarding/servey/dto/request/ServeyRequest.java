package com.onboarding.servey.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.onboarding.servey.domain.Servey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "설문조사 요청")
@Getter
@NoArgsConstructor
public class ServeyRequest {

	@ApiModelProperty(notes = "설문조사 이름", example = "INNER CIRCLE 풀스택 개발 코스 사전 조사", required = true, dataType = "string")
	@NotEmpty(message = "설문조사 이름이 입력되지 않았습니다.")
	private String name;

	@ApiModelProperty(notes = "설문조사 설명", example = "안녕하세요? 패스트캠퍼스입니다.", required = true, dataType = "string")
	@NotEmpty(message = "설문조사 설명이 입력되지 않았습니다.")
	private String description;

	@ApiModelProperty(notes = "설문 받을 항목")
	@Size(min = 1, max = 10, message = "설문 받을 항목은 1개 ~ 10개까지 포함 할 수 있습니다.")
	@Valid
	private List<QuestionRequest> questions;

	public Servey of() {
		return Servey.builder()
			.name(name)
			.description(description)
			.build();
	}
}


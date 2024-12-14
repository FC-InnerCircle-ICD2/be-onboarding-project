package com.onboarding.servey.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.onboarding.servey.domain.Servey;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ServeyResponse {

	private Long id;

	@ApiModelProperty(notes = "설문조사 이름", example = "INNER CIRCLE 풀스택 개발 코스 사전 조사",
		required = true, dataType = "string")
	private String name;

	@ApiModelProperty(notes = "설문조사 설명", example = "안녕하세요? 패스트캠퍼스입니다.",
		required = true, dataType = "string")
	private String description;

	@ApiModelProperty(notes = "설문 받을 항목")
	private List<QuestionResponse> questions = new ArrayList<>();

	@Builder
	public ServeyResponse(Long id, String name, String description, List<QuestionResponse> questions) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.questions = questions;
	}

	public static ServeyResponse from(Servey servey) {
		return ServeyResponse.builder()
			.id(servey.getId())
			.name(servey.getName())
			.description(servey.getDescription())
			.questions(servey.getQuestions().stream().map(QuestionResponse::from).collect(Collectors.toList()))
			.build();
	}
}

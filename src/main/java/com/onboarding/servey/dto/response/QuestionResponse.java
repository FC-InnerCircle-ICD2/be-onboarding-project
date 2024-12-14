package com.onboarding.servey.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.onboarding.servey.domain.Question;
import com.onboarding.servey.domain.QuestionSnapShot;
import com.onboarding.servey.model.QuestionType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionResponse {

	private Long id;

	@ApiModelProperty(notes = "항목 이름", example = "연락처를 작성해 주세요.", required = true, dataType = "string")
	private String name;

	@ApiModelProperty(notes = "항목 설명", example = "ex) 010-0000-0000", dataType = "string")
	private String description;

	@ApiModelProperty(notes = "항목 입력 형태", example = "SHORT_TYPE", required = true,
		allowableValues = "SHORT_TYPE, LONG_TYPE, SINGLE_LIST, MULTI_LIST", dataType = "string")
	private QuestionType type;

	@ApiModelProperty(notes = "항목 필수 여부", example = "true", required = true, dataType = "boolean")
	private boolean required;

	@ApiModelProperty(notes = "선택할 수 있는 후보")
	private List<OptionResponse> options = new ArrayList<>();

	private List<AnswerResponse> answers = new ArrayList<>();

	@Builder
	public QuestionResponse(Long id, String name, String description, QuestionType type, boolean required,
		List<OptionResponse> options, List<AnswerResponse> answers) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.required = required;
		this.options = options;
		this.answers = answers;
	}

	public static QuestionResponse from(Question question) {
		return QuestionResponse.builder()
			.id(question.getId())
			.name(question.getName())
			.description(question.getDescription())
			.type(question.getType())
			.required(question.isRequired())
			.options(question.getOptions().stream().map(OptionResponse::from).collect(Collectors.toList()))
			.build();
	}

	public static QuestionResponse from(QuestionSnapShot question) {
		return QuestionResponse.builder()
			.id(question.getQuestionId())
			.name(question.getName())
			.description(question.getDescription())
			.type(question.getType())
			.required(question.isRequired())
			.build();
	}
}

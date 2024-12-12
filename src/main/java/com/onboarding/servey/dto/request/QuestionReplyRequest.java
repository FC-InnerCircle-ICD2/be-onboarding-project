package com.onboarding.servey.dto.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "설문 받을 항목 응답")
@Getter
@NoArgsConstructor
public class QuestionReplyRequest {

	@ApiModelProperty(notes = "설문 받을 항목 식별자", example = "1", required = true, dataType = "long")
	@NotNull
	private Long id;

	private AnswerRequest answerRequest;
}

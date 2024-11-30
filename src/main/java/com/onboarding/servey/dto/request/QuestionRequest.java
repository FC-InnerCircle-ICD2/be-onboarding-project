package com.onboarding.servey.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.onboarding.common.validation.Type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "설문 받을 항목")
@Getter
@NoArgsConstructor
public class QuestionRequest {

	@ApiModelProperty(notes = "항목 이름", example = "연락처를 작성해 주세요.", required = true, dataType = "string")
	@NotEmpty(message = "설문 받을 항목 이름이 입력되지 않았습니다.")
	private String name;

	@ApiModelProperty(notes = "항목 설명", example = "ex) 010-0000-0000", dataType = "string")
	private String description;

	@ApiModelProperty(notes = "항목 입력 형태", example = "SINGLE_LIST", required = true,
		allowableValues = "SHORT_TYPE, LONG_TYPE, SINGLE_LIST, MULTI_LIST", dataType = "string")
	@Type
	private String type;

	@ApiModelProperty(notes = "항목 필수 여부", example = "true", required = true, dataType = "boolean")
	private boolean required;

	@ApiModelProperty(notes = "선택할 수 있는 후보")
	@Valid
	private List<OptionRequest> options;
}

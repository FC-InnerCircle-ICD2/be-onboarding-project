package com.onboarding.servey.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "설문 받을 항목 응답")
@Getter
@NoArgsConstructor
public class AnswerRequest {

	@ApiModelProperty(notes = "설문 받을 항목 식별자", example = "1", required = true, dataType = "int")
	private Long id;

	@ApiModelProperty(notes = "설문 받을 항목 응답",
		example = "항목 입력 형태가 MULTI_LIST 인 경우 선택할 수 있는 후보 식별자를 파이프(|)로 연결해주세요. ex) 1|3",
		dataType = "string")
	private String answer;
}

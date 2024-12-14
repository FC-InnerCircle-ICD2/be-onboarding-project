package com.onboarding.servey.dto.response;

import com.onboarding.servey.domain.Option;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OptionResponse {

	private Long id;

	@ApiModelProperty(notes = "번호", example = "1", required = true, dataType = "int")
	private int number;

	@Builder
	public OptionResponse(Long id, int number) {
		this.id = id;
		this.number = number;
	}

	public static OptionResponse from(Option option) {
		return OptionResponse.builder()
			.id(option.getId())
			.number(option.getNumber())
			.build();
	}
}

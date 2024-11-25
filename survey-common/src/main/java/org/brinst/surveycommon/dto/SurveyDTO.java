package org.brinst.surveycommon.dto;

import java.util.List;

import org.brinst.surveycommon.enums.OptionType;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SurveyDTO {

	@Getter
	@AllArgsConstructor
	public static class ReqDTO {
		private final String name;
		private final String description;
		private final List<ReqItemDTO> itemList;
	}

	@Getter
	@AllArgsConstructor
	public static class ReqItemDTO {
		private final String name;
		private final String description;
		private final boolean required;
		private final OptionType type;
		private final List<String> options;
	}
}

package org.brinst.surveycommon.dto;

import java.util.List;

import org.brinst.surveycommon.enums.OptionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SurveyDTO {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ReqDTO {
		private String name;
		private String description;
		private List<ItemDTO> itemList;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ItemDTO {
		private String name;
		private String description;
		private boolean required;
		private OptionType type;
		private List<String> options;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ResDTO {
		private String name;
		private String description;
		private int version;
		private List<ItemResDTO> itemList;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ItemResDTO {
		private Long id;
		private String name;
		private String description;
		private boolean required;
		private OptionType type;
		private List<String> options;
	}

}

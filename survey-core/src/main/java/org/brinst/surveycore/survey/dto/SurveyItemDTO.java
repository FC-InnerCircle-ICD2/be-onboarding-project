package org.brinst.surveycore.survey.dto;

import java.util.List;

import org.brinst.surveycommon.enums.OptionType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME, // JSON에 타입 정보를 문자열로 저장
	include = JsonTypeInfo.As.PROPERTY, // 타입 정보를 속성으로 포함
	property = "type" // JSON에서 타입 정보를 담을 필드 이름
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = SurveyItemDTO.ShortAnswer.class, name = "SHORT_ANSWER"),
	@JsonSubTypes.Type(value = SurveyItemDTO.LongAnswer.class, name = "LONG_ANSWER"),
	@JsonSubTypes.Type(value = SurveyItemDTO.SingleChoice.class, name = "SINGLE_CHOICE"),
	@JsonSubTypes.Type(value = SurveyItemDTO.MultiChoice.class, name = "MULTIPLE_CHOICE")
})
public abstract class SurveyItemDTO {
	private String name;
	private String description;
	private boolean required;
	private OptionType type;
	@JsonIgnore
	public abstract OptionType getOptionType();
	@JsonIgnore
	public abstract List<String> getOptionValue();

	@Getter
	@NoArgsConstructor
	public static class ShortAnswer extends SurveyItemDTO {

		public ShortAnswer(String name, String description, boolean required, OptionType type) {
			super(name, description, required, type);
		}

		@Override
		public OptionType getOptionType() {
			return OptionType.SHORT_ANSWER;
		}

		@Override
		public List<String> getOptionValue() {
			return List.of();
		}
	}

	@Getter
	@NoArgsConstructor
	public static class LongAnswer extends SurveyItemDTO {

		public LongAnswer(String name, String description, boolean required, OptionType type) {
			super(name, description, required, type);
		}

		@Override
		public OptionType getOptionType() {
			return OptionType.LONG_ANSWER;
		}

		@Override
		public List<String> getOptionValue() {
			return List.of();
		}
	}

	@Getter
	@NoArgsConstructor
	public static class SingleChoice extends SurveyItemDTO {
		private String option;

		public SingleChoice(String name, String description, boolean required, OptionType type, String option) {
			super(name, description, required, type);
			this.option = option;
		}

		@Override
		public OptionType getOptionType() {
			return OptionType.SINGLE_CHOICE;
		}

		@Override
		public List<String> getOptionValue() {
			return List.of(option);
		}
	}

	@Getter
	@NoArgsConstructor
	public static class MultiChoice extends SurveyItemDTO {
		private List<String> options;

		public MultiChoice(String name, String description, boolean required, OptionType type, List<String> options) {
			super(name, description, required, type);
			this.options = options;
		}

		@Override
		public OptionType getOptionType() {
			return OptionType.MULTIPLE_CHOICE;
		}

		@Override
		public List<String> getOptionValue() {
			return options;
		}
	}
}

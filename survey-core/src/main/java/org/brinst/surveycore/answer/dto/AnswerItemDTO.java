package org.brinst.surveycore.answer.dto;

import java.util.List;

import org.brinst.surveycommon.enums.OptionType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = AnswerItemDTO.ShortAnswer.class, name = "SHORT_ANSWER"),
	@JsonSubTypes.Type(value = AnswerItemDTO.LongAnswer.class, name = "LONG_ANSWER"),
	@JsonSubTypes.Type(value = AnswerItemDTO.SingleChoice.class, name = "SINGLE_CHOICE"),
	@JsonSubTypes.Type(value = AnswerItemDTO.MultiChoice.class, name = "MULTI_CHOICE")
})
public abstract class AnswerItemDTO {
	private Long itemId;
	private OptionType type;

	@JsonIgnore
	public abstract Object getAnswerValue();

	public AnswerItemDTO(Long itemId, OptionType type) {
		this.itemId = itemId;
		this.type = type;
	}

	@Getter
	@NoArgsConstructor
	public static class ShortAnswer extends AnswerItemDTO {
		private String answer;

		public ShortAnswer(Long itemId, OptionType type, String answer) {
			super(itemId, type);
			this.answer = answer;
		}

		@Override
		public String  getAnswerValue() {
			return answer;
		}
	}

	@Getter
	@NoArgsConstructor
	public static class LongAnswer extends AnswerItemDTO {
		private String answer;

		public LongAnswer(Long itemId, OptionType type, String answer) {
			super(itemId, type);
			this.answer = answer;
		}

		@Override
		public Object getAnswerValue() {
			return answer;
		}
	}

	@Getter
	@NoArgsConstructor
	public static class SingleChoice extends AnswerItemDTO {
		private String option;

		public SingleChoice(Long itemId, OptionType type, String option) {
			super(itemId, type);
			this.option = option;
		}

		@Override
		public String getAnswerValue() {
			return option;
		}
	}

	@Getter
	@NoArgsConstructor
	public static class MultiChoice extends AnswerItemDTO {
		private List<String> options;

		public MultiChoice(Long itemId, OptionType type, List<String> options) {
			super(itemId, type);
			this.options = options;
		}

		@Override
		public List<String> getAnswerValue() {
			return options;
		}
	}
}

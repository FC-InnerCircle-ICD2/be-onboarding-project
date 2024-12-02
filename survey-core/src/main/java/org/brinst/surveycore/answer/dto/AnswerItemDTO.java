package org.brinst.surveycore.answer.dto;

import java.util.List;

import org.brinst.surveycommon.enums.OptionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AnswerItemDTO {
	private Long itemId;
	private OptionType type;

	@Getter
	@NoArgsConstructor
	public static class ShortAnswer extends AnswerItemDTO {
		private String answer;

		public ShortAnswer(Long itemId, OptionType type, String answer) {
			super(itemId, type);
			this.answer = answer;
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
	}

	@Getter
	@NoArgsConstructor
	public static class SingleChoice extends AnswerItemDTO {
		private String option;

		public SingleChoice(Long itemId, OptionType type, String option) {
			super(itemId, type);
			this.option = option;
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
	}
}

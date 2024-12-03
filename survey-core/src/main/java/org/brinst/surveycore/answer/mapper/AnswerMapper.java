package org.brinst.surveycore.answer.mapper;

import java.util.List;

import org.brinst.surveycore.answer.dto.AnswerItemDTO;
import org.brinst.surveycore.answer.entity.AnswerParent;

public class AnswerMapper {
	public static AnswerItemDTO convertAnswerToDTO(AnswerParent answerParent) {
		return switch (answerParent.getOptionType()) {
			case SHORT_ANSWER -> new AnswerItemDTO.ShortAnswer(answerParent.getId(), answerParent.getOptionType(),
				answerParent.getAnswerValue().toString());
			case LONG_ANSWER -> new AnswerItemDTO.LongAnswer(answerParent.getId(), answerParent.getOptionType(),
				answerParent.getAnswerValue().toString());
			case SINGLE_CHOICE -> new AnswerItemDTO.SingleChoice(answerParent.getId(), answerParent.getOptionType(),
				answerParent.getAnswerValue().toString());
			case MULTIPLE_CHOICE -> new AnswerItemDTO.MultiChoice(answerParent.getId(), answerParent.getOptionType(),
				(List<String>)answerParent.getAnswerValue());
		};
	}
}

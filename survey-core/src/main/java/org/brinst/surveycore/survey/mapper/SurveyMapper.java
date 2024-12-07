package org.brinst.surveycore.survey.mapper;

import java.util.List;

import org.brinst.surveycore.survey.dto.SurveyItemDTO;
import org.brinst.surveycore.survey.entity.SurveyQuestion;

public class SurveyMapper {
	public static SurveyItemDTO convertAnswerToDTO(SurveyQuestion answerParent) {
		return switch (answerParent.getOptionType()) {
			case SHORT_ANSWER -> new SurveyItemDTO.ShortAnswer(
				answerParent.getName(),
				answerParent.getDescription(),
				answerParent.isRequired(),
				answerParent.getOptionType());
			case LONG_ANSWER -> new SurveyItemDTO.LongAnswer(
				answerParent.getName(),
				answerParent.getDescription(),
				answerParent.isRequired(),
				answerParent.getOptionType());
			case SINGLE_CHOICE -> new SurveyItemDTO.SingleChoice(
				answerParent.getName(),
				answerParent.getDescription(),
				answerParent.isRequired(),
				answerParent.getOptionType(),
				answerParent.getOption().toString());
			case MULTIPLE_CHOICE -> new SurveyItemDTO.MultiChoice(
				answerParent.getName(),
				answerParent.getDescription(),
				answerParent.isRequired(),
				answerParent.getOptionType(),
				(List<String>)answerParent.getOption());
		};
	}
}

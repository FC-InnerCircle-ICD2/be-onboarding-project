package org.brinst.surveycore.answer.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AnswerDTO {
	@Getter
	@AllArgsConstructor
	public static class ResDTO {
		private Long answerId;
		private int surveyVersion;
		private List<AnswerItemDTO> answerItemResDTOS;
	}
}

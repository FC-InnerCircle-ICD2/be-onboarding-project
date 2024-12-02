package org.brinst.surveycore.answer.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AnswerDTO {
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ReqDTO {
		private Long itemId;
		private List<String> answers;
	}

	@Getter
	@AllArgsConstructor
	public static class ResDTO {
		private Long answerId;
		private int surveyVersion;
		private List<AnswerItemDTO> answerItemResDTOS;
	}
}

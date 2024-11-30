package org.brinst.surveycommon.dto;

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
		private List<AnswerItemResDTO> answerItemResDTOS;
	}

	@Getter
	@AllArgsConstructor
	public static class AnswerItemResDTO {
		private SurveyDTO.ItemResDTO question;
		private List<String> answers;
	}
}

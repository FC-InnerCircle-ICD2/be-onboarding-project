package org.brinst.surveycommon.dto;

import java.util.List;

import lombok.Getter;

public class AnswerDTO {
	@Getter
	public static class ReqDTO {
		private Long itemId;
		private List<String> answers;
	}
}

package org.brinst.surveycore.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.brinst.surveycommon.config.GlobalException;
import org.brinst.surveycommon.dto.AnswerDTO;
import org.brinst.surveycommon.dto.SurveyDTO;
import org.brinst.surveycommon.enums.ErrorCode;
import org.springframework.util.CollectionUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SurveyVersion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int version;
	@ManyToOne
	private Survey survey;
	@OneToMany(mappedBy = "surveyVersion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SurveyQuestion> surveyQuestions = new ArrayList<>();

	public SurveyVersion(int version, List<SurveyDTO.ItemDTO> items, Survey survey) {
		this.version = version;
		this.survey = survey;
		if (!CollectionUtils.isEmpty(items)) {
			this.surveyQuestions = items.stream().map(question
				-> new SurveyQuestion(question, this)).toList();
		}
	}

	public void validateAnswer(List<AnswerDTO.ReqDTO> answers) {
		Map<Long, SurveyQuestion> surveyQuestionMap = surveyQuestions.stream()
			.collect(Collectors.toMap(SurveyQuestion::getId, surveyQuestion -> surveyQuestion));
		Map<Long, AnswerDTO.ReqDTO> answerItemIds = answers.stream()
			.collect(Collectors.toMap(AnswerDTO.ReqDTO::getItemId, answer -> answer));

		surveyQuestionMap.forEach((id, question) -> {
			if (!answerItemIds.containsKey(id)) {
				throw new GlobalException(ErrorCode.BAD_REQUEST);
			}
			AnswerDTO.ReqDTO reqDTO = answerItemIds.get(id);
			question.validateRequiredAnswer(reqDTO.getAnswers());
			question.validateAnswerByOptionType(reqDTO.getAnswers());
			question.validateAnswerByChoice(reqDTO.getAnswers());
		});
	}
}

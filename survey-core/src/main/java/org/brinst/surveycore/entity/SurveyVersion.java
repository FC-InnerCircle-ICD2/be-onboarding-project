package org.brinst.surveycore.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.brinst.surveycommon.dto.AnswerDTO;
import org.brinst.surveycommon.dto.SurveyDTO;
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

	public boolean validateAnswer(List<AnswerDTO.ReqDTO> answers) {
		Set<Long> surveyQuestionIds = surveyQuestions.stream()
			.map(SurveyQuestion::getId)
			.collect(Collectors.toSet());

		Set<Long> answerItemIds = answers.stream()
			.map(AnswerDTO.ReqDTO::getItemId)
			.collect(Collectors.toSet());

		return surveyQuestionIds.equals(answerItemIds);
	}
}

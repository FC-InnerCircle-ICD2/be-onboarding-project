package org.brinst.surveycore.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.brinst.surveycommon.dto.AnswerDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Survey survey;
	@ManyToOne
	private SurveyVersion surveyVersion;
	@OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AnswerItem> answers = new ArrayList<>();

	public static Answer registerAnswer(List<AnswerDTO.ReqDTO> answerDTO, SurveyVersion surveyVersion) {
		Map<Long, SurveyQuestion> surveyQuestionMap = surveyVersion.getSurveyQuestions().stream()
			.collect(Collectors.toMap(SurveyQuestion::getId, surveyQuestion -> surveyQuestion));
		Map<Long, AnswerDTO.ReqDTO> answerItemIds = answerDTO.stream()
			.collect(Collectors.toMap(AnswerDTO.ReqDTO::getItemId, answer -> answer));

		Answer answerEntity = new Answer(
			surveyVersion.getSurvey(),
			surveyVersion
		);

		surveyQuestionMap.forEach((id, question) -> {
			AnswerDTO.ReqDTO reqDTO = answerItemIds.get(id);
			List<String> answer = reqDTO.getAnswers();
			AnswerItem answerItem = new AnswerItem(answer, answerEntity, question);
			answerEntity.getAnswers().add(answerItem);
		});
		return answerEntity;
	}

	public Answer(Survey survey, SurveyVersion surveyVersion) {
		this.survey = survey;
		this.surveyVersion = surveyVersion;
	}
}

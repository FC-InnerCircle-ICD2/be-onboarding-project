package org.brinst.surveycore.answer.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.brinst.surveycore.answer.dto.AnswerItemDTO;
import org.brinst.surveycore.answer.service.ValidatorService;
import org.brinst.surveycore.survey.entity.Survey;
import org.brinst.surveycore.survey.entity.SurveyQuestion;
import org.brinst.surveycore.survey.entity.SurveyVersion;

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
	private List<AnswerParent> answers = new ArrayList<>();

	public static Answer registerAnswer(List<AnswerItemDTO> answerDTO, SurveyVersion surveyVersion,
		ValidatorService validatorService) {
		Map<Long, SurveyQuestion> surveyQuestionMap = surveyVersion.getSurveyQuestions().stream()
			.collect(Collectors.toMap(SurveyQuestion::getId, surveyQuestion -> surveyQuestion));
		Map<Long, AnswerItemDTO> answerItemIds = answerDTO.stream()
			.collect(Collectors.toMap(AnswerItemDTO::getItemId, answer -> answer));

		Answer answerEntity = new Answer(
			surveyVersion.getSurvey(),
			surveyVersion
		);

		validatorService.validate(surveyVersion, answerDTO);

		surveyQuestionMap.forEach((id, question) -> {
			AnswerItemDTO answerReq = answerItemIds.get(id);
			// 필수값이 아닌 질문에 대해 null 응답 또는 빈 리스트인 경우 등록을 건너뛰기
			if (answerReq == null || answerReq.getAnswerValue() == null) {
				return;
			}
			AnswerParent answerItemEntity = createAnswer(answerEntity, question, answerReq);
			answerEntity.getAnswers().add(answerItemEntity);
		});
		return answerEntity;
	}

	private static AnswerParent createAnswer(Answer answer, SurveyQuestion question, AnswerItemDTO answers) {
		return switch (question.getOptionType()) {
			case SHORT_ANSWER -> new ShortAnswer(answer, question, (String)answers.getAnswerValue());
			case LONG_ANSWER -> new LongAnswer(answer, question, (String)answers.getAnswerValue());
			case SINGLE_CHOICE -> new SingleChoiceAnswer(answer, question, (String)answers.getAnswerValue());
			case MULTIPLE_CHOICE -> new MultiChoiceAnswer(answer, question, (List<String>)answers.getAnswerValue());
		};
	}

	public Answer(Survey survey, SurveyVersion surveyVersion) {
		this.survey = survey;
		this.surveyVersion = surveyVersion;
	}
}

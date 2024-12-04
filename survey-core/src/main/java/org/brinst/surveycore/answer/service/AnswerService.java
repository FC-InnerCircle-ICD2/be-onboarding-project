package org.brinst.surveycore.answer.service;

import java.util.List;

import org.brinst.surveycore.answer.dto.AnswerDTO;
import org.brinst.surveycore.answer.entity.Answer;
import org.brinst.surveycore.answer.mapper.AnswerMapper;
import org.brinst.surveycore.answer.repository.AnswerRepository;
import org.brinst.surveycore.survey.entity.SurveyVersion;
import org.brinst.surveycore.survey.service.SurveyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;
	private final ValidatorService validatorService;
	private final SurveyService surveyService;

	@Transactional
	public void answerSurvey(Long surveyId, List<AnswerDTO.ReqDTO> answers) {
		SurveyVersion latestSurveyVersion = surveyService.getLatestSurveyVersion(surveyId);
		Answer answer = Answer.registerAnswer(answers, latestSurveyVersion, validatorService);
		answerRepository.save(answer);
	}

	@Transactional(readOnly = true)
	public List<AnswerDTO.ResDTO> getAnswerBySurveyIdAndCondition(Long surveyId, String questionName, String answerValue) {
		List<Answer> answerList = answerRepository.searchAnswers(surveyId, questionName, answerValue);
		return answerList.stream()
			.map(answer -> new AnswerDTO.ResDTO(
			answer.getId(),
			answer.getSurveyVersion().getVersion(),
			answer.getAnswers().stream().map(AnswerMapper::convertAnswerToDTO).toList())
		).toList();
	}

}

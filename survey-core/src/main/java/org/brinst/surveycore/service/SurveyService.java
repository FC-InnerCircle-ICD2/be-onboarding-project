package org.brinst.surveycore.service;

import java.util.List;

import org.brinst.surveycommon.config.GlobalException;
import org.brinst.surveycommon.dto.AnswerDTO;
import org.brinst.surveycommon.dto.SurveyDTO;
import org.brinst.surveycommon.enums.ErrorCode;
import org.brinst.surveycore.entity.Answer;
import org.brinst.surveycore.entity.Survey;
import org.brinst.surveycore.entity.SurveyOption;
import org.brinst.surveycore.entity.SurveyVersion;
import org.brinst.surveycore.repository.AnswerRepository;
import org.brinst.surveycore.repository.SurveyRepository;
import org.brinst.surveycore.repository.SurveyVersionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyService {
	private final SurveyRepository surveyRepository;
	private final SurveyVersionRepository surveyVersionRepository;
	private final AnswerRepository answerRepository;

	@Transactional
	public void registerSurvey(SurveyDTO.ReqDTO surveyReqDTO) {
		if (surveyReqDTO == null) {
			throw new GlobalException(ErrorCode.BAD_REQUEST);
		}
		if (!surveyReqDTO.getItemList().isEmpty() && surveyReqDTO.getItemList().size() <= 10) {
			surveyRepository.save(new Survey(surveyReqDTO));
		} else {
			throw new GlobalException(ErrorCode.CHECK_SURVEY_ITEM_COUNT);
		}
	}

	@Transactional
	public void modifySurvey(Long surveyId, SurveyDTO.ReqDTO surveyReqDTO) {
		Survey survey = surveyRepository.findById(surveyId).orElseThrow();
		survey.modifySurvey(surveyReqDTO);
	}

	@Transactional(readOnly = true)
	public SurveyDTO.ResDTO getSurveyById(Long surveyId) {
		Survey survey = surveyRepository.findById(surveyId).orElseThrow();
		//survey 최신 버전 조회
		int version = survey.getVersion();
		SurveyVersion surveyVersion = surveyVersionRepository.findByVersionAndSurvey(version, survey);
		return new SurveyDTO.ResDTO(
			survey.getName(),
			survey.getDescription(),
			surveyVersion.getVersion(),
			surveyVersion.getSurveyQuestions().stream().map(question -> new SurveyDTO.ItemResDTO(
				question.getId(),
				question.getName(),
				question.getDescription(),
				question.isRequired(),
				question.getOptionType(),
				question.getSurveyOptions().stream().map(SurveyOption::getOption).toList()
			)).toList()
		);
	}

	@Transactional
	public void answerSurvey(Long surveyId, List<AnswerDTO.ReqDTO> answers) {
		SurveyVersion latestSurveyVersion = getLatestSurveyVersion(surveyId);
		latestSurveyVersion.validateAnswer(answers);
		Answer answer = Answer.registerAnswer(answers, latestSurveyVersion);
		answerRepository.save(answer);
	}

	@Transactional(readOnly = true)
	public List<AnswerDTO.ResDTO> getAnswerBySurveyId(Long surveyId) {
		List<Answer> answerList = answerRepository.findBySurvey_Id(surveyId);
		return answerList.stream().map(answer -> new AnswerDTO.ResDTO(
			answer.getId(),
			answer.getSurveyVersion().getVersion(),
			answer.getAnswers().stream().map(
				answerItem -> new AnswerDTO.AnswerItemResDTO(
					new SurveyDTO.ItemResDTO(
						answerItem.getSurveyQuestion().getId(),
						answerItem.getSurveyQuestion().getName(),
						answerItem.getSurveyQuestion().getDescription(),
						answerItem.getSurveyQuestion().isRequired(),
						answerItem.getSurveyQuestion().getOptionType(),
						answerItem.getSurveyQuestion().getSurveyOptions().stream().map(SurveyOption::getOption).toList()
					),
					answerItem.getAnswerValue()
				)
			).toList())
		).toList();
	}

	private SurveyVersion getLatestSurveyVersion(Long surveyId) {
		Survey survey = surveyRepository.findById(surveyId).orElseThrow();
		//survey 최신 버전 조회
		int version = survey.getVersion();
		return surveyVersionRepository.findByVersionAndSurvey(version, survey);
	}
}

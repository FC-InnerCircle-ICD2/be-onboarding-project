package org.brinst.surveycore.service;

import java.util.List;

import org.brinst.surveycommon.config.GlobalException;
import org.brinst.surveycommon.dto.AnswerDTO;
import org.brinst.surveycommon.dto.SurveyDTO;
import org.brinst.surveycommon.enums.ErrorCode;
import org.brinst.surveycore.entity.Survey;
import org.brinst.surveycore.entity.SurveyOption;
import org.brinst.surveycore.entity.SurveyVersion;
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
			surveyVersion.getSurveyQuestions().stream().map(question -> new SurveyDTO.ItemDTO(
				question.getName(),
				question.getDescription(),
				question.isRequired(),
				question.getOptionType(),
				question.getSurveyOptions().stream().map(SurveyOption::getOption).toList()
			)).toList()
		);
	}

	public void surveyAnswer(Long surveyId, List<AnswerDTO.ReqDTO> answers) {
		SurveyVersion latestSurveyVersion = getLatestSurveyVersion(surveyId);
		if (!latestSurveyVersion.validateAnswer(answers)) {
			throw new GlobalException(ErrorCode.BAD_REQUEST);
		}
	}

	private SurveyVersion getLatestSurveyVersion(Long surveyId) {
		Survey survey = surveyRepository.findById(surveyId).orElseThrow();
		//survey 최신 버전 조회
		int version = survey.getVersion();
		return surveyVersionRepository.findByVersionAndSurvey(version, survey);
	}

}

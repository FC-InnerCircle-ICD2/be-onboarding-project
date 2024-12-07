package org.brinst.surveycore.survey.service;

import org.brinst.surveycommon.enums.ErrorCode;
import org.brinst.surveycommon.exception.GlobalException;
import org.brinst.surveycore.survey.dto.SurveyDTO;
import org.brinst.surveycore.survey.entity.Survey;
import org.brinst.surveycore.survey.entity.SurveyVersion;
import org.brinst.surveycore.survey.mapper.SurveyMapper;
import org.brinst.surveycore.survey.repository.SurveyRepository;
import org.brinst.surveycore.survey.repository.SurveyVersionRepository;
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
		Survey survey = surveyRepository.findById(surveyId).orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
		//survey 최신 버전 조회
		int version = survey.getVersion();
		SurveyVersion surveyVersion = surveyVersionRepository.findByVersionAndSurvey(version, survey);
		return new SurveyDTO.ResDTO(
			survey.getName(),
			survey.getDescription(),
			surveyVersion.getVersion(),
			surveyVersion.getSurveyQuestions().stream().map(SurveyMapper::convertAnswerToDTO).toList());
	}

	public SurveyVersion getLatestSurveyVersion(Long surveyId) {
		Survey survey = surveyRepository.findById(surveyId).orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
		//survey 최신 버전 조회
		int version = survey.getVersion();
		return surveyVersionRepository.findByVersionAndSurvey(version, survey);
	}
}

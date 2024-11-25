package org.brinst.surveycore.service;

import org.brinst.surveycommon.dto.SurveyDTO;
import org.brinst.surveycore.entity.Survey;
import org.brinst.surveycore.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyService {
	private final SurveyRepository surveyRepository;

	public void registerSurvey(SurveyDTO.ReqDTO surveyReqDTO) {
		surveyRepository.save(new Survey(surveyReqDTO));
	}
}

package org.brinst.surveycore.service;

import org.brinst.surveycore.survey.repository.SurveyRepository;
import org.brinst.surveycore.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AnswerServiceTests {
	@Autowired
	private SurveyService surveyService;
	@Autowired
	private SurveyRepository surveyRepository;

	// @Test
	// @DisplayName("설문조사의 정상 응답 테스트")
	// void test1() {
	// 	//given
	// 	registerSurvey();
	// 	SurveyDTO.ResDTO givenSurvey = surveyService.getSurveyById(1L);
	// 	//when
	// 	surveyService.answerSurvey(1L, List.of(
	// 		new AnswerDTO.ReqDTO(
	// 			givenSurvey.getItemList().get(0).getId(),
	// 			List.of("단답형 응답")
	// 		),
	// 		new AnswerDTO.ReqDTO(
	// 			givenSurvey.getItemList().get(1).getId(),
	// 			null
	// 		),
	// 		new AnswerDTO.ReqDTO(
	// 			givenSurvey.getItemList().get(2).getId(),
	// 			List.of("확인")
	// 		),
	// 		new AnswerDTO.ReqDTO(
	// 			givenSurvey.getItemList().get(3).getId(),
	// 			List.of("빨강","초록")
	// 		)
	// 	));
	// }
	//
	// void registerSurvey() {
	// 	surveyService.registerSurvey(new SurveyDTO.ReqDTO(
	// 		"설문조사1",
	// 		"박희찬에 대한 설문조사입니다.",
	// 		List.of(
	// 			new SurveyDTO.ItemDTO(
	// 				"단답형1",
	// 				"단답형 설문입니다.",
	// 				true,
	// 				OptionType.SHORT_ANSWER,
	// 				null
	// 			),
	// 			new SurveyDTO.ItemDTO(
	// 				"장문형2",
	// 				"장문형 설문입니다.",
	// 				false,
	// 				OptionType.LONG_ANSWER,
	// 				null
	// 			),
	// 			new SurveyDTO.ItemDTO(
	// 				"단일 선택 리스트",
	// 				"단일 선택 리스트 설문 입니다.",
	// 				true,
	// 				OptionType.SINGLE_CHOICE,
	// 				List.of("확인")
	// 			),
	// 			new SurveyDTO.ItemDTO(
	// 				"다중 선택 리스트",
	// 				"다중 선택 리스트 설문 입니다.",
	// 				true,
	// 				OptionType.MULTIPLE_CHOICE,
	// 				List.of("빨강","파랑","초록")
	// 			)
	// 		)
	// 	));
	// }
}

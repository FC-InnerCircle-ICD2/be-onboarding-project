package org.brinst.surveycore.service;

import org.brinst.surveycore.survey.repository.SurveyRepository;
import org.brinst.surveycore.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class SurveyServiceTests {
	@Autowired
	private SurveyService surveyService;
	@Autowired
	private SurveyRepository surveyRepository;

	// @Test
	// @DisplayName("항목이 없는 설문조사를 추가 할 수 있다.")
	// void test1() {
	// 	//given
	// 	SurveyDTO.ReqDTO reqDTO = new SurveyDTO.ReqDTO("test", "test description", null);
	// 	//when
	// 	surveyService.registerSurvey(reqDTO);
	// 	//then
	// 	Survey survey = surveyRepository.findById(1L).orElseThrow();
	// 	assertThat(survey).isNotNull();
	// 	assertThat(survey.getName()).isEqualTo(reqDTO.getName());
	// 	assertThat(survey.getDescription()).isEqualTo(reqDTO.getDescription());
	// 	assertThat(survey.getSurveyQuestions()).isEmpty();
	// }
	//
	// @Test
	// @DisplayName("단답형 설문조사를 추가 할 수 있다.")
	// void test2() {
	// 	//given
	// 	SurveyDTO.ItemDTO item1 = new SurveyDTO.ItemDTO("단답형1", "단답형1의 설명입니다.", true, OptionType.SHORT_ANSWER,
	// 		null);
	// 	SurveyDTO.ReqDTO reqDTO = new SurveyDTO.ReqDTO("test", "test description", List.of(
	// 		item1
	// 	));
	// 	//when
	// 	surveyService.registerSurvey(reqDTO);
	// 	//then
	// 	Survey survey = surveyRepository.findById(1L).orElseThrow();
	// 	assertThat(survey).isNotNull();
	// 	assertThat(survey.getName()).isEqualTo(reqDTO.getName());
	// 	assertThat(survey.getDescription()).isEqualTo(reqDTO.getDescription());
	// 	assertThat(survey.getSurveyQuestions()).isNotEmpty();
	// 	assertThat(survey.getSurveyQuestions()).hasSize(1);
	// 	assertThat(survey.getSurveyQuestions().get(0).getOptionType()).isEqualTo(item1.getType());
	// 	assertThat(survey.getSurveyQuestions().get(0).getName()).isEqualTo(item1.getName());
	// 	assertThat(survey.getSurveyQuestions().get(0).getDescription()).isEqualTo(item1.getDescription());
	// }
	//
	// @Test
	// @DisplayName("여러개의 단답형 장문형 설문조사를 추가 할 수 있다.")
	// void test3() {
	// 	//given
	// 	SurveyDTO.ItemDTO item1 = new SurveyDTO.ItemDTO("단답형1", "단답형1의 설명입니다.", true, OptionType.SHORT_ANSWER,
	// 		null);
	// 	SurveyDTO.ItemDTO item2 = new SurveyDTO.ItemDTO("단답형2", "단답형2의 설명입니다.", true, OptionType.SHORT_ANSWER,
	// 		null);
	// 	SurveyDTO.ItemDTO item3 = new SurveyDTO.ItemDTO("장문형1장문형1장문형1장문형1장문형1장문형1장문형1장문형1장문형1장문형1장문형1장문형1",
	// 		"장문형1의 설명입니다.", false, OptionType.LONG_ANSWER,
	// 		null);
	// 	SurveyDTO.ItemDTO item4 = new SurveyDTO.ItemDTO("장문형2장문형2장문형2장문형2장문형2장문형2장문형2장문형2장문형2장문형2장문형2장문형2",
	// 		"장문형2의 설명입니다.", true, OptionType.LONG_ANSWER,
	// 		null);
	// 	SurveyDTO.ReqDTO reqDTO = new SurveyDTO.ReqDTO("test", "test description", List.of(
	// 		item1,item2,item3,item4
	// 	));
	// 	//when
	// 	surveyService.registerSurvey(reqDTO);
	// 	//then
	// 	Survey survey = surveyRepository.findById(1L).orElseThrow();
	// 	assertThat(survey).isNotNull();
	// 	assertThat(survey.getName()).isEqualTo(reqDTO.getName());
	// 	assertThat(survey.getDescription()).isEqualTo(reqDTO.getDescription());
	// 	assertThat(survey.getSurveyQuestions()).isNotEmpty();
	// 	assertThat(survey.getSurveyQuestions()).hasSize(4);
	// 	assertThat(survey.getSurveyQuestions().get(0).getOptionType()).isEqualTo(item1.getType());
	// 	assertThat(survey.getSurveyQuestions().get(0).getName()).isEqualTo(item1.getName());
	// 	assertThat(survey.getSurveyQuestions().get(0).getDescription()).isEqualTo(item1.getDescription());
	// 	assertThat(survey.getSurveyQuestions().get(1).getOptionType()).isEqualTo(item2.getType());
	// 	assertThat(survey.getSurveyQuestions().get(1).getName()).isEqualTo(item2.getName());
	// 	assertThat(survey.getSurveyQuestions().get(1).getDescription()).isEqualTo(item2.getDescription());
	// 	assertThat(survey.getSurveyQuestions().get(2).getOptionType()).isEqualTo(item3.getType());
	// 	assertThat(survey.getSurveyQuestions().get(2).getName()).isEqualTo(item3.getName());
	// 	assertThat(survey.getSurveyQuestions().get(2).getDescription()).isEqualTo(item3.getDescription());
	// 	assertThat(survey.getSurveyQuestions().get(3).getOptionType()).isEqualTo(item4.getType());
	// 	assertThat(survey.getSurveyQuestions().get(3).getName()).isEqualTo(item4.getName());
	// 	assertThat(survey.getSurveyQuestions().get(3).getDescription()).isEqualTo(item4.getDescription());
	// }
}

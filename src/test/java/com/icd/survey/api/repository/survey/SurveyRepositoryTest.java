package com.icd.survey.api.repository.survey;

import com.icd.survey.api.entity.survey.ItemAnswer;
import com.icd.survey.api.entity.survey.ItemAnswerOption;
import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.SurveyItem;
import com.icd.survey.api.entity.survey.dto.ItemAnswerDto;
import com.icd.survey.api.entity.survey.dto.ItemAnswerOptionDto;
import com.icd.survey.api.entity.survey.dto.SurveyDto;
import com.icd.survey.api.entity.survey.dto.SurveyItemDto;
import com.icd.survey.api.enums.survey.ResponseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SurveyRepositoryTest {
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    SurveyItemRepository surveyItemRepository;
    @Autowired
    ItemAnswerRepository itemAnswerRepository;
    @Autowired
    AnswerOptionRepository answerOptionRepository;
    private final String IP_ADDRESS = "0.0.0.1";

    private Long surveySeq;
    private Long itemSeq;
    private Long optionSeq1;
    private Long optionSeq2;
    private Long answerSeq1;
    private Long answerSeq2;

    @BeforeEach
    @DisplayName("create survey")
    void createSurvey() {
        SurveyDto surveyDto =
                SurveyDto.builder()
                        .surveyName("survey name")
                        .surveyDescription("survey description")
                        .ipAddress(IP_ADDRESS)
                        .build();
        SurveyItemDto surveyItemDto =
                SurveyItemDto.builder()
                        .itemName("item name")
                        .itemDescription("item description")
                        .isEssential(Boolean.TRUE)
                        .itemResponseType(ResponseType.SHORT_ANSWER.getType())
                        .build();
        ItemAnswerOptionDto optionDto =
                ItemAnswerOptionDto.builder()
                        .option("test option")
                        .build();
        ItemAnswerOptionDto optionDto1 =
                ItemAnswerOptionDto.builder()
                        .option("test option1")
                        .build();

        Survey survey = surveyRepository.save(Survey.createSurveyRequest(surveyDto));
        surveySeq = survey.getSurveySeq();

        surveyItemDto.setSurveySeq(survey.getSurveySeq());
        SurveyItem surveyItem = surveyItemRepository.save(SurveyItem.createSurveyItemRequest(surveyItemDto));
        itemSeq = surveyItem.getItemSeq();

        optionDto.setItemSeq(surveyItem.getItemSeq());
        optionDto1.setItemSeq(surveyItem.getItemSeq());
        ItemAnswerOption answerOption = answerOptionRepository.save(ItemAnswerOption.createItemResponseOptionRequest(optionDto));
        ItemAnswerOption answerOption1 = answerOptionRepository.save(ItemAnswerOption.createItemResponseOptionRequest(optionDto1));
        optionSeq1 = answerOption.getOptionSeq();
        optionSeq2 = answerOption1.getOptionSeq();

        ItemAnswerDto answerDto =
                ItemAnswerDto
                        .builder()
                        .answerSeq(answerOption.getOptionSeq())
                        .itemSeq(surveyItem.getItemSeq())
                        .isOptionalAnswer(Boolean.TRUE)
                        .build();
        ItemAnswerDto answerDto1 =
                ItemAnswerDto
                        .builder()
                        .answerSeq(answerOption1.getOptionSeq())
                        .itemSeq(surveyItem.getItemSeq())
                        .isOptionalAnswer(Boolean.TRUE)
                        .build();

        ItemAnswer answer = itemAnswerRepository.save(ItemAnswer.createItemResponseRequest(answerDto));
        ItemAnswer answer1 = itemAnswerRepository.save(ItemAnswer.createItemResponseRequest(answerDto1));
        answerSeq1 = answer.getAnswerSeq();
        answerSeq2 = answer1.getAnswerSeq();
    }

    @Test
    @DisplayName("설문조사 이름과 계정(ip 주소)로 설문조사 유무 조회")
    void existsBySurveyNameAndIpAddressTest() {
        // given
        String surveyName = "survey name";
        //when
        Boolean result = surveyRepository.existsBySurveyNameAndIpAddress(surveyName, IP_ADDRESS);
        //then
        assertThat(result).isEqualTo(Boolean.TRUE);
    }

    @Test
    @DisplayName("설문조사 seq 로 아이템 리스트 조회")
    void finaItemListBySurveySeq() {
        // given
        Long surveySeq = this.surveySeq;
        //when
        Optional<List<SurveyItem>> optionalSurveyItemList = surveyItemRepository.findAllBySurveySeq(surveySeq);
        //then
        assertThat(optionalSurveyItemList).isPresent();
        assertThat(optionalSurveyItemList.get()).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("설문조사 항목 seq 로 선택형 항목 옵션 리스트 조회")
    void findOptionListByItemSeq() {
        // given
        Long itemSeq = this.itemSeq;
        //when
        Optional<List<ItemAnswerOption>> optionalItemList = answerOptionRepository.findAllByItemSeq(itemSeq);
        //then
        assertThat(optionalItemList).isPresent();
        assertThat(optionalItemList.get()).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("설문조사 항목 seq와 항목 옵션 seq 로 해당 선택 항목 옵션 조회")
    void findOptionBySeqAndItemSeq() {
        // given
        Long itemSeq = this.itemSeq;
        Long optionSeq1 = this.optionSeq1;
        Long optionSeq2 = this.optionSeq2;
        //when
        Optional<ItemAnswerOption> option1 = answerOptionRepository.findByOptionSeqAndItemSeq(optionSeq1, itemSeq);
        Optional<ItemAnswerOption> option2 = answerOptionRepository.findByOptionSeqAndItemSeq(optionSeq2, itemSeq);
        //then
        assertThat(option1).isPresent();
        assertThat(option2).isPresent();
    }

    @Test
    @DisplayName("설문조사 항목 seq 로 답변 리스트 조회")
    void findAnswerListByItemSeq() {
        // given
        Long itemSeq = this.itemSeq;
        //when
        Optional<List<ItemAnswer>> optionalAnswerList = itemAnswerRepository.findAllByItemSeq(itemSeq);
        //then
        assertThat(optionalAnswerList).isPresent();
        assertThat(optionalAnswerList.get()).hasSizeGreaterThanOrEqualTo(2);
    }
}

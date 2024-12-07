package com.icd.survey.api.business.survey;

import com.icd.survey.api.controller.survey.request.ItemOptionRequest;
import com.icd.survey.api.controller.survey.request.SurveyItemRequest;
import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.SurveyItem;
import com.icd.survey.api.entity.survey.dto.SurveyDto;
import com.icd.survey.api.entity.survey.dto.SurveyItemDto;
import com.icd.survey.api.entity.survey.repository.AnswerOptionRepository;
import com.icd.survey.api.entity.survey.repository.ItemAnswerRepository;
import com.icd.survey.api.entity.survey.repository.SurveyItemRepository;
import com.icd.survey.api.entity.survey.repository.SurveyRepository;
import com.icd.survey.api.entity.survey.repository.query.SurveyQueryRepository;
import com.icd.survey.api.service.survey.business.SurveyActionBusiness;
import com.icd.survey.api.service.survey.business.SurveyQueryBusiness;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurveyActionBusinessTest {
    @InjectMocks
    @Spy
    SurveyActionBusiness surveyActionBusiness;

    @Mock
    SurveyRepository surveyRepository;
    @Mock
    SurveyItemRepository surveyItemRepository;
    @Mock
    ItemAnswerRepository itemAnswerRepository;
    @Mock
    AnswerOptionRepository answerOptionRepository;
    @Mock
    SurveyQueryBusiness surveyQueryBusiness;
    @Mock
    SurveyQueryRepository surveyQueryRepository;

    private final String IP_ADDRESS = "0.0.0.1";


    @Test
    @DisplayName("inject dependencies test")
    void contextLoads() {
        assertThat(this).isNotNull();
    }

    /**
     * 나머지 기본 save 테스트는 skip
     */
    @Test
    @DisplayName("설문조사 저장")
    void saveSurvey() {
        // given
        SurveyDto surveyDto =
                SurveyDto
                        .builder()
                        .surveyName("survey name")
                        .surveyDescription("survey description")
                        .ipAddress(IP_ADDRESS)
                        .build();
        //when
        Survey requestSurvey = Survey.createSurveyRequest(surveyDto);
        when(surveyRepository.save(any())).thenReturn(requestSurvey);
        Survey survey = surveyActionBusiness.saveSurvey(surveyDto);
        //then
        assertThat(survey).isEqualTo(requestSurvey);
    }

    @Test
    @DisplayName("설문조사 항목들 저장")
    void saveSurveyItemList() {
        // given
        SurveyItemRequest mockRequest1 = mock(SurveyItemRequest.class);
        SurveyItemRequest mockRequest2 = mock(SurveyItemRequest.class);
        Long surveySeq = 1L;

        List<SurveyItemRequest> itemRequestList = List.of(mockRequest1, mockRequest2);

        doNothing().when(mockRequest1).validationCheck();
        doNothing().when(mockRequest2).validationCheck();
        when(mockRequest1.createSurveyItemDtoRequest()).thenReturn(new SurveyItemDto());
        when(mockRequest2.createSurveyItemDtoRequest()).thenReturn(new SurveyItemDto());
        when(mockRequest1.isChoiceType()).thenReturn(Boolean.TRUE);
        when(mockRequest2.isChoiceType()).thenReturn(Boolean.FALSE);
        List<ItemOptionRequest> optionRequestList = new ArrayList<>();
        when(mockRequest1.getOptionList()).thenReturn(optionRequestList);
        SurveyItem item1 = mock(SurveyItem.class);
        SurveyItemDto dto1 = mockRequest1.createSurveyItemDtoRequest();
        SurveyItemDto dto2 = mockRequest2.createSurveyItemDtoRequest();
        when(surveyActionBusiness.saveSurveyItem(dto1)).thenReturn(item1);
        when(surveyActionBusiness.saveSurveyItem(dto2)).thenReturn(item1);
        doNothing().when(surveyActionBusiness).saveItemOptionList(optionRequestList, 1L);
        Long itemSeq1 = 1L;
        when(item1.getItemSeq()).thenReturn(itemSeq1);

        // when
        surveyActionBusiness.saveSurveyItemList(itemRequestList, surveySeq);

        // then
        verify(surveyActionBusiness, times(2)).saveSurveyItem(any(SurveyItemDto.class));
    }

    @Test
    @DisplayName("설문조사 seq 로 기존 설문 항목들 disabled 처리")
    void updateSurveyItemAsDisabled() {
        // given
        Long surveySeq = 1L;
        doNothing().when(surveyQueryRepository).updateSurveyItemAsDisabled(surveySeq);
        // when
        surveyQueryRepository.updateSurveyItemAsDisabled(surveySeq);
        // then
        verify(surveyQueryRepository).updateSurveyItemAsDisabled(surveySeq);
    }

    // todo : 설문조사 응답 저장 메서드 테스트
}

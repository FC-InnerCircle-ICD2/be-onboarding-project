package com.icd.survey.api.service.survey;

import com.icd.survey.api.dto.survey.request.ItemOptionRequest;
import com.icd.survey.api.dto.survey.request.SurveyItemRequest;
import com.icd.survey.api.dto.survey.request.SurveyRequest;
import com.icd.survey.api.enums.survey.ResponseType;
import com.icd.survey.api.repository.survey.ResponseOptionRepository;
import com.icd.survey.api.repository.survey.SurveyItemRepository;
import com.icd.survey.api.repository.survey.SurveyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SurveyServiceIntegrationTest {

    @Mock
    SurveyRepository surveyRepository;
    @Mock
    SurveyItemRepository surveyItemRepository;
    @Mock
    ResponseOptionRepository responseOptionRepository;
    @InjectMocks
    SurveyService surveyService;

    @Test
    @DisplayName("설문조사 생성 메서드 통합 테스트")
    void createSurveyTest() {
        ItemOptionRequest optionRequest = ItemOptionRequest
                .builder()
                .option("first option")
                .build();

        SurveyItemRequest itemRequest = SurveyItemRequest
                .builder()
                .itemName("test survey item name")
                .itemDescription("test survey item descripton")
                .itemResponseType(ResponseType.SINGLE_CHOICE.getType())
                .isEssential(Boolean.TRUE)
                .build();

        SurveyRequest surveyRequest = SurveyRequest
                .builder()
                .surveyName("test survey name")
                .surveyDescription("test survey description")
                .ipAddress("127.0.0.1")
                .build();

        itemRequest.setOptionList(List.of(optionRequest));
        surveyRequest.setSurveyItemList(List.of(itemRequest));

        surveyService.createSurvey(surveyRequest);
    }

}

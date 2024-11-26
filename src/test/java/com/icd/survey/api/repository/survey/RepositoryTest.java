package com.icd.survey.api.repository.survey;

import com.icd.survey.api.dto.survey.request.ItemOptionRequest;
import com.icd.survey.api.dto.survey.request.SurveyItemRequest;
import com.icd.survey.api.dto.survey.request.SurveyRequest;
import com.icd.survey.api.entity.survey.ItemResponseOption;
import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.SurveyItem;
import com.icd.survey.api.enums.survey.ResponseType;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import java.util.List;

@DataJpaTest
public class RepositoryTest {
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    SurveyItemRepository surveyItemRepository;
    @Autowired
    ResponseOptionRepository responseOptionRepository;
    @Autowired
    ItemResponseRepository itemResponseRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    void setTestSurvey() {
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
        Survey requestSurvey = surveyRequest.toEntity();
        SurveyItem requestSurveyItem = itemRequest.toEntity();
        ItemResponseOption requestResponseOption = optionRequest.toEntity();

        Survey survey = surveyRepository.save(requestSurvey);

        requestSurveyItem.setSurvey(survey);
        SurveyItem surveyItem = surveyItemRepository.save(requestSurveyItem);

        requestResponseOption.setSurveyItem(surveyItem);
        responseOptionRepository.save(requestResponseOption);
    }

    @Test
    @DisplayName("기존 설문조사 항목들 삭제 테스트")
    void makeAllAsDeletedBySurveySeqTest() {
        Survey survey = surveyRepository.findById(1L).get();

        List<SurveyItem> surveyItemList = surveyItemRepository.findBySurveyAndIsDeletedTrue(survey).get();

        SurveyItem item = surveyItemList.get(0);
        surveyItemRepository.makeAllAsDeletedBySurveySeq(1L);

        em.clear();

        List<SurveyItem> afterDeleteSurveyItemList = surveyItemRepository.findBySurvey(survey).get();
        SurveyItem afterItem = afterDeleteSurveyItemList.get(0);
        assertThat(item.getIsDeleted()).isNotEqualTo(afterItem.getIsDeleted());
    }
}

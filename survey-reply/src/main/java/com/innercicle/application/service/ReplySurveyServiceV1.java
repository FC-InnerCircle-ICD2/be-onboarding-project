package com.innercicle.application.service;

import com.innercicle.adapter.out.service.v1.dto.Survey;
import com.innercicle.advice.exceptions.NotMatchedException;
import com.innercicle.advice.exceptions.RequiredFieldException;
import com.innercicle.annotations.RedissonLock;
import com.innercicle.annotations.UseCase;
import com.innercicle.application.port.in.v1.ItemOptionCommandV1;
import com.innercicle.application.port.in.v1.ReplySurveyCommandV1;
import com.innercicle.application.port.in.v1.ReplySurveyItemCommandV1;
import com.innercicle.application.port.in.v1.ReplySurveyUsecaseV1;
import com.innercicle.application.port.out.ReplySurveyOutPortV1;
import com.innercicle.application.port.out.v1.SearchSurveyOutPortV1;
import com.innercicle.domain.ReplySurvey;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ReplySurveyServiceV1 implements ReplySurveyUsecaseV1 {

    private final SearchSurveyOutPortV1 searchSurveyOutPortV1;
    private final ReplySurveyOutPortV1 replySurveyOutPortV1;

    @Override
    @RedissonLock("#replySurveyCommandV1.getSurveyId()")
    public ReplySurvey replySurvey(ReplySurveyCommandV1 replySurveyCommandV1) {
        validateRequestSurveyItemWithSavedSurvey(replySurveyCommandV1);
        return replySurveyOutPortV1.replySurvey(replySurveyCommandV1.mapToDomain()); // 필요한 경우 실제 로직으로 반환값 수정
    }

    /**
     * <h2>요청된 설문 항목이 저장된 설문과 일치하는지 확인</h2>
     * 각 항목이 순서를 일치시키기 위해 식별자로 정렬을 시킨다.( 식별자는 반드시 순서가 있고, 이후에 만든 식별자는 반드시 이전 식별자 보다 정렬 후순위다 ) {@link com.innercicle.generator.GeneralIdGenerator} <br/>
     * 저장된 설문의 항목과 응답한 설문의 항목을 비교하여 일치하지 않으면 예외를 발생시킨다.
     *
     * @param replySurveyCommandV1 응답한 설문
     */
    private void validateRequestSurveyItemWithSavedSurvey(ReplySurveyCommandV1 replySurveyCommandV1) {
        Survey survey = searchSurveyOutPortV1.getSurvey(replySurveyCommandV1.getSurveyId());
        if (survey == null) {
            throw new RequiredFieldException("설문이 존재하지 않습니다.");
        }

        validateSurvey(replySurveyCommandV1, survey);

        List<Survey.SurveyItem> savedItems = getSortedSurveyItems(survey);
        List<ReplySurveyItemCommandV1> replyItems = getSortedReplyItems(replySurveyCommandV1);

        if (savedItems.size() != replyItems.size()) {
            throw new NotMatchedException("설문 항목이 일치하지 않습니다.");
        }

        validateSurveyItems(savedItems, replyItems);
    }

    private List<Survey.SurveyItem> getSortedSurveyItems(Survey survey) {
        return !CollectionUtils.isEmpty(survey.getItems()) ? survey.getItems().stream()
            .sorted(Comparator.comparing(Survey.SurveyItem::getId))
            .toList() : Collections.emptyList();
    }

    private List<ReplySurveyItemCommandV1> getSortedReplyItems(ReplySurveyCommandV1 replySurveyCommandV1) {
        return !CollectionUtils.isEmpty(replySurveyCommandV1.getItems()) ? replySurveyCommandV1.getItems().stream()
            .sorted(Comparator.comparing(ReplySurveyItemCommandV1::getId))
            .toList() : Collections.emptyList();
    }

    private void validateSurveyItems(List<Survey.SurveyItem> savedItems, List<ReplySurveyItemCommandV1> replyItems) {
        for (int i = 0; i < savedItems.size(); i++) {
            Survey.SurveyItem savedItem = savedItems.get(i);
            ReplySurveyItemCommandV1 replyItem = replyItems.get(i);
            validateItem(savedItem, replyItem);
            validateOptions(savedItem, replyItem);
        }
    }

    private static void validateSurvey(ReplySurveyCommandV1 replySurveyCommandV1, Survey survey) {
        if (!survey.getName().equals(replySurveyCommandV1.getName())) {
            throw new NotMatchedException("설문명이 일치하지 않습니다.");
        }
        if (!survey.getDescription().equals(replySurveyCommandV1.getDescription())) {
            throw new NotMatchedException("설문 설명이 일치하지 않습니다.");
        }
    }

    private static void validateOptions(Survey.SurveyItem savedItem, ReplySurveyItemCommandV1 item) {

        for (int j = 0; j < savedItem.getOptions().size(); j++) {
            String option = savedItem.getOptions().get(j);
            ItemOptionCommandV1 itemOptionCommandV1 = item.getOptions().get(j);
            if (!option.equals(itemOptionCommandV1.getOption())) {
                throw new NotMatchedException("설문 항목 옵션이 일치하지 않습니다.");
            }
        }
    }

    private static void validateItem(Survey.SurveyItem savedItem, ReplySurveyItemCommandV1 item) {
        validateCondition(
            savedItem.getItem().equals(item.getItem()),
            "설문 항목이 일치하지 않습니다."
        );
        validateCondition(
            savedItem.getDescription().equals(item.getDescription()),
            "설문 항목 설명이 일치하지 않습니다."
        );
        validateCondition(
            savedItem.getInputType() == item.getInputType(),
            "설문 항목 입력 타입이 일치하지 않습니다."
        );
        validateCondition(
            savedItem.isRequired() == item.isRequired(),
            "설문 항목 필수 여부가 일치하지 않습니다."
        );
        validateCondition(
            savedItem.getOptions().size() == item.getOptions().size(),
            "설문 항목 옵션 개수가 일치하지 않습니다."
        );
    }

    private static void validateCondition(boolean condition, String errorMessage) {
        if (!condition) {
            throw new NotMatchedException(errorMessage);
        }
    }

}

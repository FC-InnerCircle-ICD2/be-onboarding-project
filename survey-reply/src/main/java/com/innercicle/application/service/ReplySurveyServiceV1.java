package com.innercicle.application.service;

import com.innercicle.adapter.out.service.v1.dto.Survey;
import com.innercicle.advice.exceptions.NotMatchedException;
import com.innercicle.annotations.UseCase;
import com.innercicle.application.port.in.v1.ItemOptionCommandV1;
import com.innercicle.application.port.in.v1.ReplySurveyCommandV1;
import com.innercicle.application.port.in.v1.ReplySurveyItemCommandV1;
import com.innercicle.application.port.in.v1.ReplySurveyUsecaseV1;
import com.innercicle.application.port.out.v1.SearchSurveyOutPortV1;
import com.innercicle.domain.ReplySurvey;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ReplySurveyServiceV1 implements ReplySurveyUsecaseV1 {

    private final SearchSurveyOutPortV1 searchSurveyOutPortV1;

    @Override
    public ReplySurvey replySurvey(ReplySurveyCommandV1 replySurveyCommandV1) {
        Survey survey = searchSurveyOutPortV1.getSurvey(replySurveyCommandV1.getSurveyId());
        if (survey == null) {
            return null; // Survey가 없을 경우 null 반환
        }

        validateSurvey(replySurveyCommandV1, survey);

        List<Survey.SurveyItem> savedItems = getSortedSurveyItems(survey);
        List<ReplySurveyItemCommandV1> replyItems = getSortedReplyItems(replySurveyCommandV1);

        if (savedItems.size() != replyItems.size()) {
            throw new NotMatchedException("설문 항목이 일치하지 않습니다.");
        }

        validateSurveyItems(savedItems, replyItems);

        return null; // 필요한 경우 실제 로직으로 반환값 수정
    }

    private List<Survey.SurveyItem> getSortedSurveyItems(Survey survey) {
        return survey.getItems().stream()
            .sorted(Comparator.comparing(Survey.SurveyItem::getId))
            .toList();
    }

    private List<ReplySurveyItemCommandV1> getSortedReplyItems(ReplySurveyCommandV1 replySurveyCommandV1) {
        return replySurveyCommandV1.getItems().stream()
            .sorted(Comparator.comparing(ReplySurveyItemCommandV1::getId))
            .toList();
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

package com.practice.survey.surveymngt.repository.impl;

import com.practice.survey.response.model.entity.QResponse;
import com.practice.survey.responseItem.model.entity.QResponseItem;
import com.practice.survey.surveyItem.model.entity.QSurveyItem;
import com.practice.survey.surveyVersion.model.entity.QSurveyVersion;
import com.practice.survey.surveymngt.model.dto.QSurveyResponseDto;
import com.practice.survey.surveymngt.model.dto.SurveyResponseDto;
import com.practice.survey.surveymngt.model.entity.QSurvey;
import com.practice.survey.surveymngt.repository.wrapper.SurveyRespositoryWrapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SurveyRespositoryWrapperImpl implements SurveyRespositoryWrapper {

    private final JPAQueryFactory queryFactory;

    QSurvey survey = QSurvey.survey;
    QSurveyVersion surveyVersion = QSurveyVersion.surveyVersion;
    QSurveyItem surveyItem = QSurveyItem.surveyItem;
    QResponse response = QResponse.response;
    QResponseItem responseItem = QResponseItem.responseItem;

    @Override
    public List<SurveyResponseDto> getSurveyResponse(Long surveyId, String itemName, String responseValue) {

        Predicate predicate = createPredicate(surveyId, itemName, responseValue);

        return queryFactory
                .select(new QSurveyResponseDto(
                        survey.name,
                        survey.description,
                        surveyVersion.versionNumber,
                        surveyItem.name,
                        surveyItem.description,
                        surveyItem.itemNumber,
                        response.respondentId,
                        responseItem.responseValue
                ))
                .from(survey)
                .join(surveyVersion).on(surveyVersion.survey.eq(survey))
                .join(surveyItem).on(surveyItem.version.eq(surveyVersion))
                .leftJoin(response).on(response.version.eq(surveyVersion))
                .leftJoin(responseItem).on(responseItem.response.eq(response).and(responseItem.item.eq(surveyItem)))
                .where(predicate)
                .orderBy(survey.surveyId.asc(),
                        surveyVersion.versionId.asc(),
                        surveyItem.itemId.asc(),
                        response.responseId.asc(),
                        responseItem.responseItemId.asc())
                .fetch();
    }

    private Predicate createPredicate(Long surveyId, String itemName, String responseValue) {

        BooleanBuilder builder = new BooleanBuilder();

        if(surveyId != null) {
            builder.and(survey.surveyId.eq(surveyId));
        }

        if(itemName != null && !itemName.trim().isEmpty()) {
            builder.and(surveyItem.name.containsIgnoreCase(itemName));
        }

        if(responseValue != null && !responseValue.trim().isEmpty()) {
            builder.and(responseItem.responseValue.containsIgnoreCase(responseValue));
        }

        return builder;
    }
}

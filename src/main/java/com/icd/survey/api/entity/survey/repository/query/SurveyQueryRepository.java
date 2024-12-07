package com.icd.survey.api.entity.survey.repository.query;

import com.icd.survey.api.entity.survey.dto.ItemAnswerDto;
import com.icd.survey.api.entity.survey.dto.SurveyDto;
import com.icd.survey.api.entity.survey.dto.SurveyItemDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.icd.survey.api.entity.survey.QItemAnswer.itemAnswer;
import static com.icd.survey.api.entity.survey.QSurvey.survey;
import static com.icd.survey.api.entity.survey.QSurveyItem.surveyItem;

@Repository
@RequiredArgsConstructor
public class SurveyQueryRepository {

    private final JPQLQueryFactory queryFactory;

    public void updateSurveyItemAsDisabled(Long surveySeq) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(surveyItem.surveySeq.eq(surveySeq));
        queryFactory
                .update(surveyItem)
                .set(surveyItem.isDisabled, Boolean.TRUE)
                .where(builder)
                .execute();
    }

    /*public SurveyDto getSurveyById(Long surveySeq) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(survey.surveySeq.eq(surveySeq));
        return queryFactory
                .selectFrom(survey)
                .leftJoin(surveyItem).on(survey.surveySeq.eq(surveyItem.surveySeq))
                .leftJoin(itemAnswer).on(surveyItem.itemSeq.eq(itemAnswer.itemSeq))
                .where(condition)
                .transform(GroupBy.groupBy(survey.surveySeq)
                        .list(
                                Projections.fields(
                                        SurveyDto.class,
                                        survey.surveySeq,
                                        survey.surveyName,
                                        survey.surveyDescription,
                                        survey.regDate,
                                        survey.modDate,
                                        GroupBy.list(
                                                Projections.fields(
                                                        SurveyItemDto.class,
                                                        surveyItem.itemSeq,
                                                        surveyItem.itemName,
                                                        surveyItem.itemDescription,
                                                        surveyItem.isEssential,
                                                        GroupBy.list(
                                                                Projections.fields(
                                                                        ItemAnswerDto.class,
                                                                        itemAnswer.answerSeq,
                                                                        itemAnswer.isOptionalAnswer,
                                                                        itemAnswer.answer,
                                                                        itemAnswer.optionSeq,
                                                                        itemAnswer.optionAnswer
                                                                )
                                                        ).as("itemAnswerList")
                                                )
                                        ).as("surveyItemList")
                                )
                        )).get(0);
    }*/


}

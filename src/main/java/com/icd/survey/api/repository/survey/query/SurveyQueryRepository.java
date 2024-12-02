package com.icd.survey.api.repository.survey.query;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.icd.survey.api.entity.survey.QItemAnswer.itemAnswer;
import static com.icd.survey.api.entity.survey.QSurvey.survey;
import static com.icd.survey.api.entity.survey.QSurveyItem.surveyItem;

@Repository
@RequiredArgsConstructor
public class SurveyQueryRepository {

    private final JPQLQueryFactory queryFactory;



}

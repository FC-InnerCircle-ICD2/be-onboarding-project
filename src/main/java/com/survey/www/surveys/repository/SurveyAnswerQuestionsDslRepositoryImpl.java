package com.survey.www.surveys.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SurveyAnswerQuestionsDslRepositoryImpl implements SurveyAnswerQuestionsDslRepository {
    private final JPAQueryFactory factory;
}

package com.survey.www.surveys.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SurveyQuestionsOptionsDslRepositoryImpl implements SurveyQuestionsOptionsDslRepository {
    private final JPAQueryFactory factory;
}

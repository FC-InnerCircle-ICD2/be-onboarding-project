package com.metsakurr.beonboardingproject.domain.survey.repository;

import com.metsakurr.beonboardingproject.domain.survey.entity.QOption;
import com.metsakurr.beonboardingproject.domain.survey.entity.QQuestion;
import com.metsakurr.beonboardingproject.domain.survey.entity.QSurvey;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SurveyRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void save(Survey survey) {
        entityManager.persist(survey);
    }

    public Survey findById(long idx) {
        QSurvey survey = QSurvey.survey;
        QQuestion question = QQuestion.question;
        QOption option = QOption.option;

        return queryFactory.select(survey)
                .from(survey)
                .leftJoin(question).on(question.survey.eq(survey))
                .leftJoin(option).on(option.question.eq(question))
                .where(survey.idx.eq(idx))
                .fetchOne();
    }
}

package com.metsakurr.beonboardingproject.domain.survey.repository;

import com.metsakurr.beonboardingproject.domain.survey.entity.QQuestion;
import com.metsakurr.beonboardingproject.domain.survey.entity.QSurvey;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SurveyRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public Survey save(Survey survey) {
        entityManager.persist(survey);
        return survey;
    }

    public Optional<Survey> findById(long idx) {
        QSurvey survey = QSurvey.survey;
        QQuestion question = QQuestion.question;
//        QOption option = QOption.option;

        return Optional.ofNullable(
                queryFactory.select(survey)
                        .from(survey)
                        .leftJoin(survey.questions, question).fetchJoin()
//                        .leftJoin(question.options, option)
                        .where(survey.idx.eq(idx))
                        .fetchOne()
        );
    }
}

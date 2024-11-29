package com.metsakurr.beonboardingproject.domain.answer.repository;

import com.metsakurr.beonboardingproject.domain.answer.entity.QAnswer;
import com.metsakurr.beonboardingproject.domain.answer.entity.QResponse;
import com.metsakurr.beonboardingproject.domain.answer.entity.Response;
import com.metsakurr.beonboardingproject.domain.survey.entity.QQuestion;
import com.metsakurr.beonboardingproject.domain.survey.entity.QSurvey;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ResponseRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void save(Response response) {
        entityManager.persist(response);
    }

    public Response findById(long idx) {
        QResponse response = QResponse.response;
        QSurvey survey = QSurvey.survey;
        QAnswer answer = QAnswer.answer;
        QQuestion question = QQuestion.question;

        return queryFactory
                .select(response)
                .from(response)
                .leftJoin(response.survey, survey).fetchJoin()
                .leftJoin(response.answers, answer).fetchJoin()
                .leftJoin(answer.question, question).fetchJoin()
                .where(response.idx.eq(idx))
                .fetchOne();
    }
}

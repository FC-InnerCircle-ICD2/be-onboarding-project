package com.metsakurr.beonboardingproject.domain.survey.repository;

import com.metsakurr.beonboardingproject.domain.survey.entity.QQuestion;
import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void save(Question question) {
        entityManager.persist(question);
    }

    public List<Question> findAllBySurveyId(long surveyId) {
        QQuestion question = QQuestion.question;
        return queryFactory.selectFrom(question)
                .where(question.survey.idx.eq(surveyId))
                .fetch();
    }
}

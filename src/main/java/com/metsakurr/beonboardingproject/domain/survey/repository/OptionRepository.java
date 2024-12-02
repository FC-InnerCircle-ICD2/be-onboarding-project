package com.metsakurr.beonboardingproject.domain.survey.repository;

import com.metsakurr.beonboardingproject.domain.survey.entity.Option;
import com.metsakurr.beonboardingproject.domain.survey.entity.QOption;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OptionRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void save(Option option) {
        entityManager.persist(option);
    }

    public List<Option> findAllByQuestionId(long questionId) {
        QOption option = QOption.option;
        return queryFactory.selectFrom(option)
                .where(option.question.idx.eq(questionId))
                .fetch();
    }
}

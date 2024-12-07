package ziwookim.be_onboarding_project.research.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import ziwookim.be_onboarding_project.research.entity.ResearchAnswer;
import ziwookim.be_onboarding_project.research.repository.ResearchAnswerRepositoryCustom;

import java.util.List;

import static ziwookim.be_onboarding_project.research.entity.QResearchAnswer.researchAnswer;

public class ResearchAnswerRepositoryCustomImpl extends QuerydslRepositorySupport implements ResearchAnswerRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public ResearchAnswerRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        super(ResearchAnswer.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<ResearchAnswer> searchResearchAnswer(String keyword) {
        BooleanExpression itemNameMatches = researchAnswer.data.like("%\"name\":\"" + keyword + "%");
        BooleanExpression answerMatches = researchAnswer.data.like("%\"answer\":\"" + keyword + "%");

        return jpaQueryFactory.selectFrom(researchAnswer)
                .where(itemNameMatches.or(answerMatches))
                .fetch();
    }
}

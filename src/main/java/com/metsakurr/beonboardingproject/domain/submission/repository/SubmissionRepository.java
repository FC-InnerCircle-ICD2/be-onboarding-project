package com.metsakurr.beonboardingproject.domain.submission.repository;

import com.metsakurr.beonboardingproject.domain.submission.entity.QAnswer;
import com.metsakurr.beonboardingproject.domain.submission.entity.QSubmission;
import com.metsakurr.beonboardingproject.domain.submission.entity.Submission;
import com.metsakurr.beonboardingproject.domain.survey.entity.QQuestion;
import com.metsakurr.beonboardingproject.domain.survey.entity.QSurvey;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class SubmissionRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void save(Submission submission) {
        entityManager.persist(submission);
    }

    public Submission findById(long idx) {
        QSubmission submission = QSubmission.submission;
        QSurvey survey = QSurvey.survey;
        QAnswer answer = QAnswer.answer;
        QQuestion question = QQuestion.question;

        return queryFactory
                .select(submission)
                .from(submission)
                .leftJoin(submission.survey, survey).fetchJoin()
                .leftJoin(submission.answers, answer).fetchJoin()
                .leftJoin(answer.question, question).fetchJoin()
                .where(submission.idx.eq(idx))
                .fetchOne();
    }
}

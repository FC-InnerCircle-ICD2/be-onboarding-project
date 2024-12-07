package com.innercicle.adapter.out.persistence.v1;

import com.querydsl.core.BooleanBuilder;

import java.util.List;
import java.util.Optional;

import static com.innercicle.adapter.out.persistence.v1.QReplySurveyEntity.replySurveyEntity;
import static com.innercicle.adapter.out.persistence.v1.QReplySurveyItemEntity.replySurveyItemEntity;

public class ReplySurveyRepositoryImpl extends Querydsl5RepositorySupport implements ReplySurveyRepositoryQuerydsl {

    protected ReplySurveyRepositoryImpl() {
        super(ReplySurveyEntity.class);
    }

    /**
     * <h2>식별자로 설문 응답 조회</h2>
     * 식별자로 설문 응답을 조회합니다.<br/>
     *
     * @param replySurveyId 설문 응답 식별자
     * @return 설문 응답
     */
    @Override
    public Optional<ReplySurveyEntity> findByIdAndSearchKeyword(Long replySurveyId) {
        return Optional.ofNullable(
            selectFrom(replySurveyEntity)
                .join(replySurveyItemEntity).on(replySurveyItemEntity.replySurvey.eq(replySurveyEntity))
                .where(
                    replySurveyEntity.id.eq(replySurveyId)
                )
                .fetchOne()
        );
    }

    /**
     * <h2>식별자 혹은 검색어로 설문 응답 조회</h2>
     * 식별자 혹은 검색어로 설문 응답을 조회합니다.<br>
     * 검색어는 설문 응답 항목의 항목명, 응답 텍스트, 응답 옵션에 대한 검색이 가능합니다. {@link ReplySurveyItemEntity} 참조<br>
     *
     * @param replySurveyId 설문 응답 식별자
     * @param searchKeyword 검색어
     * @return 설문 응답 목록
     */
    @Override
    public List<ReplySurveyEntity> findAllByIdOrSearchKeyword(Long replySurveyId, String searchKeyword) {
        return selectFrom(replySurveyEntity)
            .join(replySurveyItemEntity).on(replySurveyItemEntity.replySurvey.eq(replySurveyEntity))
            .where(
                replySurveyEntity.id.eq(replySurveyId)
                    .or(bySearchKeyword(searchKeyword))
            )
            .fetch();
    }

    private BooleanBuilder bySearchKeyword(String searchKeyword) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(replySurveyItemEntity.item.contains(searchKeyword));
        builder.or(replySurveyItemEntity.replyText.contains(searchKeyword));
        builder.or(replySurveyItemEntity.options.any().option.contains(searchKeyword));
        return builder;
    }

}

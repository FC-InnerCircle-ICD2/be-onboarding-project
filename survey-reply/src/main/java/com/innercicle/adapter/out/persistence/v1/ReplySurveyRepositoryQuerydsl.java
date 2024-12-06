package com.innercicle.adapter.out.persistence.v1;

import java.util.List;
import java.util.Optional;

public interface ReplySurveyRepositoryQuerydsl {

    /**
     * <h2>식별자로 설문 응답 조회</h2>
     * 식별자로 설문 응답을 조회합니다.<br/>
     *
     * @param replySurveyId 설문 응답 식별자
     * @return 설문 응답
     */
    Optional<ReplySurveyEntity> findByIdAndSearchKeyword(Long replySurveyId);

    /**
     * <h2>식별자 혹은 검색어로 설문 응답 조회</h2>
     * 식별자 혹은 검색어로 설문 응답을 조회합니다.<br>
     * 검색어는 설문 응답 항목의 항목명, 응답 텍스트, 응답 옵션에 대한 검색이 가능합니다. {@link ReplySurveyItemEntity} 참조<br>
     *
     * @param replySurveyId 설문 응답 식별자
     * @param searchKeyword 검색어
     * @return 설문 응답 목록
     */
    List<ReplySurveyEntity> findAllByIdOrSearchKeyword(Long replySurveyId, String searchKeyword);

}

package com.innercicle.application.port.in.v1;

import com.innercicle.domain.v1.Survey;

public interface SearchSurveyUseCaseV1 {

    /**
     * 설문 조회
     *
     * @param searchSurveyQueryV1 설문 조회 쿼리
     * @return 설문
     */
    Survey searchSurvey(SearchSurveyQueryV1 searchSurveyQueryV1);

}

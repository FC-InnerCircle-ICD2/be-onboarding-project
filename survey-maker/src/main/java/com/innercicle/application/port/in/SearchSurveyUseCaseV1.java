package com.innercicle.application.port.in;

import com.innercicle.domain.v1.Survey;

public interface SearchSurveyUseCaseV1 {

    /**
     * 설문 조회
     *
     * @param searchSurveyQuery 설문 조회 쿼리
     * @return 설문
     */
    Survey searchSurvey(SearchSurveyQuery searchSurveyQuery);

}

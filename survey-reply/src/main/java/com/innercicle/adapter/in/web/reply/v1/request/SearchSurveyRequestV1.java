package com.innercicle.adapter.in.web.reply.v1.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchSurveyRequestV1 {

    /**
     * 설문 응답 식별자
     */
    private Long replySurveyId;

    /**
     * 검색 키워드
     */
    private String searchKeyword;

}

package com.innercicle.adapter.in.web.reply.v1.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRepliesSurveyQuery {

    /**
     * 설문 응답 식별자
     */
    private Long replySurveyId;

    /**
     * 검색 키워드
     */
    private String searchKeyword;
    
}

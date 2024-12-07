package com.innercicle.adapter.in.web.reply.v1;

import com.innercicle.adapter.in.web.reply.v1.request.SearchRepliesSurveyQuery;
import com.innercicle.adapter.in.web.reply.v1.response.SearchReplySurveyResponse;
import com.innercicle.application.port.in.v1.SearchReplySurveyQuery;
import com.innercicle.application.port.in.v1.SearchReplySurveyUsecaseV1;
import com.innercicle.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.innercicle.utils.ApiUtil.success;

@Slf4j @RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/survey/replies")
public class SearchReplySurveyControllerV1 {

    private final SearchReplySurveyUsecaseV1 searchReplySurveyUsecaseV1;

    @GetMapping("/id/{replySurveyId}")
    public ApiUtil.ApiResult<SearchReplySurveyResponse> searchReplySurvey(@PathVariable("replySurveyId") Long replySurveyId) {
        SearchReplySurveyQuery searchReplySurveyQuery =
            SearchReplySurveyQuery.builder().replySurveyId(replySurveyId).build();
        return success(
            SearchReplySurveyResponse.from(searchReplySurveyUsecaseV1.searchReplySurvey(searchReplySurveyQuery)));
    }

    @GetMapping
    public ApiUtil.ApiResult<List<SearchReplySurveyResponse>> searchReplySurvey(SearchRepliesSurveyQuery searchRepliesSurveyQuery) {
        return success(searchReplySurveyUsecaseV1.searchRepliesSurvey(searchRepliesSurveyQuery).stream().map(SearchReplySurveyResponse::from).toList());
    }

}

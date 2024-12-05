package com.innercicle.adapter.in.web.reply.v1;

import com.innercicle.adapter.in.web.reply.v1.response.SearchReplySurveyResponse;
import com.innercicle.application.port.in.v1.SearchReplySurveyQuery;
import com.innercicle.application.port.in.v1.SearchReplySurveyUsecaseV1;
import com.innercicle.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.innercicle.utils.ApiUtil.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/survey/reply")
public class SearchReplySurveyControllerV1 {

    private final SearchReplySurveyUsecaseV1 searchReplySurveyUsecaseV1;

    @GetMapping("{replySurveyId}")
    public ApiUtil.ApiResult<SearchReplySurveyResponse> searchReplySurvey(@PathVariable(name = "replySurveyId") Long replySurveyId) {
        SearchReplySurveyQuery.builder().replySurveyId(replySurveyId).build();
        return success(SearchReplySurveyResponse.from(searchReplySurveyUsecaseV1.searchReplySurvey(SearchReplySurveyQuery.builder().replySurveyId(
            replySurveyId).build())));
    }

}

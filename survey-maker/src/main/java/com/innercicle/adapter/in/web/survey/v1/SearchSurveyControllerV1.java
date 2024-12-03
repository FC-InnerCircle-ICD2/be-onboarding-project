package com.innercicle.adapter.in.web.survey.v1;

import com.innercicle.adapter.in.web.survey.v1.response.SearchSurveyResponse;
import com.innercicle.application.port.in.SearchSurveyQuery;
import com.innercicle.application.port.in.SearchSurveyUseCaseV1;
import com.innercicle.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.innercicle.utils.ApiUtil.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/survey")
public class SearchSurveyControllerV1 {

    private final SearchSurveyUseCaseV1 searchSurveyUseCaseV1;

    @GetMapping("/{surveyId}")
    public ApiUtil.ApiResult<SearchSurveyResponse> searchSurvey(@PathVariable(name = "surveyId") Long surveyId) {
        return success(SearchSurveyResponse.from(searchSurveyUseCaseV1.searchSurvey(SearchSurveyQuery.of(surveyId))));
    }

}

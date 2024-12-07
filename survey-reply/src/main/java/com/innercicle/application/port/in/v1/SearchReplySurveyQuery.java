package com.innercicle.application.port.in.v1;

import com.innercicle.validation.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchReplySurveyQuery extends SelfValidating<SearchReplySurveyQuery> {

    @NotNull(message = "설문 식별자를 입력해주세요.")
    private Long replySurveyId;

    @Builder(builderClassName = "SearchReplySurveyQueryBuilder", builderMethodName = "buildInternal")
    public static SearchReplySurveyQuery create(Long replySurveyId) {
        return new SearchReplySurveyQuery(replySurveyId);
    }

    public static class SearchReplySurveyQueryBuilder {

        public SearchReplySurveyQuery build() {
            SearchReplySurveyQuery searchReplySurveyQuery = create(replySurveyId);
            searchReplySurveyQuery.validateSelf();
            return searchReplySurveyQuery;
        }

    }

}

package com.innercicle.adapter.in.web.survey.v1.response;

import com.innercicle.domain.v1.InputType;
import com.innercicle.domain.v1.Survey;
import com.innercicle.domain.v1.SurveyItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchSurveyResponse {

    private Long id;

    private String name;

    private String description;

    private List<SearchSurveyItemResponse> items;

    public static SearchSurveyResponse from(Survey survey) {
        return SearchSurveyResponse.builder()
            .id(survey.id())
            .name(survey.name())
            .description(survey.description())
            .items(survey.items().stream()
                       .map(SearchSurveyItemResponse::from)
                       .toList())
            .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class SearchSurveyItemResponse {

        private Long id;

        /**
         * 항목
         */
        private String item;

        /**
         * 설명
         */
        private String description;

        /**
         * 입력 형태
         */
        private InputType inputType;

        /**
         * 필수 여부
         */
        private boolean required;

        private List<String> options;

        public static SearchSurveyItemResponse from(SurveyItem surveyItem) {
            return SearchSurveyItemResponse.builder()
                .id(surveyItem.id())
                .item(surveyItem.item())
                .description(surveyItem.description())
                .inputType(surveyItem.inputType())
                .required(surveyItem.required())
                .options(surveyItem.options())
                .build();
        }

    }

}

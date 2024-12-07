package com.innercicle.adapter.in.web.survey.v1.response;

import com.innercicle.domain.v1.InputType;
import com.innercicle.domain.v1.Survey;
import com.innercicle.domain.v1.SurveyItem;
import lombok.Getter;

import java.util.List;

@Getter
public class ModifySurveyResponseV1 {

    private Long id;
    private String name;
    private String description;
    private List<ModifySurveyItemResponseV1> items;

    @Getter
    static class ModifySurveyItemResponseV1 {

        private Long id;                // 식별자
        private String item;            // 항목
        private String description;     // 설명
        private InputType inputType;    // 입력 형태
        private boolean required;       // 필수 여부
        private List<String> options;   // 선택지 목록

        public static ModifySurveyItemResponseV1 from(SurveyItem surveyItem) {
            ModifySurveyItemResponseV1 responseV1 = new ModifySurveyItemResponseV1();
            responseV1.id = surveyItem.id();
            responseV1.item = surveyItem.item();
            responseV1.description = surveyItem.description();
            responseV1.inputType = surveyItem.inputType();
            responseV1.required = surveyItem.required();
            responseV1.options = surveyItem.options();
            return responseV1;
        }

    }

    public static ModifySurveyResponseV1 from(Survey survey) {
        ModifySurveyResponseV1 response = new ModifySurveyResponseV1();
        response.id = survey.id();
        response.name = survey.name();
        response.description = survey.description();
        response.items = survey.items().stream()
            .map(ModifySurveyItemResponseV1::from)
            .toList();
        return response;
    }

}

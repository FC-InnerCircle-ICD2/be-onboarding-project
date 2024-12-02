package com.innercicle.adapter.in.web.survey.v1.response;

import com.innercicle.domain.v1.InputType;
import com.innercicle.domain.v1.Survey;
import com.innercicle.domain.v1.SurveyItem;
import lombok.Getter;

import java.util.List;

/**
 * 설문 등록 완료 응답 객체
 *
 * @author seunggu.lee
 */
@Getter
public class RegisterSurveyResponseV1 {

    private Long id;
    private String name;
    private String description;
    private List<RegisterSurveyItemResponseV1> items;

    @Getter
    static class RegisterSurveyItemResponseV1 {

        private Long id;                // 식별자
        private String item;            // 항목
        private String description;     // 설명
        private InputType inputType;    // 입력 형태
        private boolean required;       // 필수 여부
        private List<String> options;   // 선택지 목록

        public static RegisterSurveyItemResponseV1 from(SurveyItem surveyItem) {
            RegisterSurveyItemResponseV1 responseV1 = new RegisterSurveyItemResponseV1();
            responseV1.id = surveyItem.id();
            responseV1.item = surveyItem.item();
            responseV1.description = surveyItem.description();
            responseV1.inputType = surveyItem.inputType();
            responseV1.required = surveyItem.required();
            responseV1.options = surveyItem.options();
            return responseV1;
        }

    }

    public static RegisterSurveyResponseV1 from(Survey survey) {
        RegisterSurveyResponseV1 response = new RegisterSurveyResponseV1();
        response.id = survey.id();
        response.name = survey.name();
        response.description = survey.description();
        response.items = survey.items().stream()
            .map(RegisterSurveyItemResponseV1::from)
            .toList();
        return response;
    }

}

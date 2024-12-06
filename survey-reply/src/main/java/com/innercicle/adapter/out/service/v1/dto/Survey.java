package com.innercicle.adapter.out.service.v1.dto;

import com.innercicle.domain.InputType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Survey {

    private Long id;

    private String name;

    private String description;

    private List<SurveyItem> items;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SurveyItem {

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

    }

}

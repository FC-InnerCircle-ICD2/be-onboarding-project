package com.innercicle.adapter.in.web.reply.v1.request;

import com.innercicle.application.port.in.v1.ItemOptionCommandV1;
import com.innercicle.application.port.in.v1.ReplySurveyCommandV1;
import com.innercicle.application.port.in.v1.ReplySurveyItemCommandV1;
import com.innercicle.domain.InputType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class ReplySurveyRequestV1 {

    /**
     * 설문 ID
     */
    private Long surveyId;

    /**
     * 응답자 email
     */
    private String replierEmail;

    private String surveyName;

    private String description;

    /**
     * 응답 목록
     */
    private List<ReplySurveyItemRequest> items;

    public ReplySurveyCommandV1 mapToCommand() {
        return ReplySurveyCommandV1.builder()
            .surveyId(this.surveyId)
            .name(this.surveyName)
            .replierEmail(this.replierEmail)
            .description(this.description)
            .items(!CollectionUtils.isEmpty(this.items) ? this.items.stream()
                .map(ReplySurveyItemRequest::mapToCommand)
                .toList() : null).build();
    }

    @Getter
    @Builder
    public static class ReplySurveyItemRequest {

        private Long id;

        private String item;

        private String description;

        private InputType inputType;

        private boolean required;

        private String replyText;

        private List<ItemOptionRequest> options;

        public ReplySurveyItemCommandV1 mapToCommand() {
            return ReplySurveyItemCommandV1.builder()
                .id(this.id)
                .item(this.item)
                .description(this.description)
                .inputType(this.inputType)
                .required(this.required)
                .replyText(this.replyText)
                .options(!CollectionUtils.isEmpty(this.options) ? this.options.stream()
                    .map(ItemOptionRequest::mapToCommand)
                    .toList() : Collections.emptyList())
                .build();
        }

        @Getter
        @Builder
        public static class ItemOptionRequest {

            private String option;

            private boolean checked;

            public ItemOptionCommandV1 mapToCommand() {
                return ItemOptionCommandV1.builder()
                    .option(this.option)
                    .checked(this.checked)
                    .build();
            }

        }

    }

}

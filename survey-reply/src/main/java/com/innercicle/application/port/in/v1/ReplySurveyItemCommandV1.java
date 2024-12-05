package com.innercicle.application.port.in.v1;

import com.innercicle.domain.InputType;
import com.innercicle.domain.ReplySurveyItem;
import com.innercicle.validation.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplySurveyItemCommandV1 extends SelfValidating<ReplySurveyItemCommandV1> {

    @NotNull(message = "식별자를 입력해주세요.")
    private Long id;

    private String item;

    private String description;

    private InputType inputType;

    private boolean required;

    private String replyText;

    private List<ItemOptionCommandV1> options;

    @Override
    public void validateSelf() {
        super.validateSelf();
        inputType.validateReplyText(replyText);
        inputType.validateOptions(!CollectionUtils.isEmpty(options) ? options.stream()
            .map(ItemOptionCommandV1::getOption)
            .toList() : Collections.emptyList());
    }

    public ReplySurveyItem mapToDomain() {
        return ReplySurveyItem.builder()
            .id(id)
            .item(item)
            .description(description)
            .inputType(inputType)
            .required(required)
            .replyText(replyText)
            .options(!CollectionUtils.isEmpty(options) ? options.stream()
                .map(ItemOptionCommandV1::mapToDomain)
                .toList() : Collections.emptyList())
            .build();
    }

    @Builder(builderClassName = "ReplySurveyItemCommandV1Builder", builderMethodName = "buildInternal")
    public static ReplySurveyItemCommandV1 create(Long id,
                                                  String item,
                                                  String description,
                                                  InputType inputType,
                                                  boolean required,
                                                  String replyText,
                                                  List<ItemOptionCommandV1> options) {
        return new ReplySurveyItemCommandV1(id, item, description, inputType, required, replyText, options);
    }

    public static class ReplySurveyItemCommandV1Builder {

        public ReplySurveyItemCommandV1 build() {
            ReplySurveyItemCommandV1 replySurveyItemCommandV1 =
                create(id, item, description, inputType, required, replyText, options);
            replySurveyItemCommandV1.validateSelf();
            return replySurveyItemCommandV1;
        }

    }

}

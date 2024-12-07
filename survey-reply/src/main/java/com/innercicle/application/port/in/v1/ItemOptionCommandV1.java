package com.innercicle.application.port.in.v1;

import com.innercicle.domain.ItemOption;
import com.innercicle.validation.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemOptionCommandV1 extends SelfValidating<ItemOptionCommandV1> {

    @NotNull(message = "설문 항목 선택지는 필수 입니다.")
    private String option;

    private boolean checked;

    public ItemOption mapToDomain() {
        return ItemOption.builder()
            .option(option)
            .checked(checked)
            .build();
    }

}

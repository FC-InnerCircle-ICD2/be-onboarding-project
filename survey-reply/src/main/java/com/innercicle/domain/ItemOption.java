package com.innercicle.domain;

import lombok.Builder;

@Builder
public record ItemOption(
    String option,        // 선택지
    boolean checked    // 선택 여부
) {

}

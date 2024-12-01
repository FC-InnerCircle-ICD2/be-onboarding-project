package com.innercicle.domain.v1;

import lombok.Builder;

import java.util.List;

/**
 * <h2>설문</h2>
 *
 * @param id          설문 ID
 * @param name        설문명
 * @param description 설문 설명
 * @param items       설문 항목 목록
 */
@Builder
public record Survey(
    Long id,
    String name,
    String description,
    List<SurveyItem> items
) {

}


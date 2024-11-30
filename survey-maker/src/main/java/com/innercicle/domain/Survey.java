package com.innercicle.domain;

import java.util.List;

/**
 * <h2>설문</h2>
 *
 * @param name        설문명
 * @param description 설문 설명
 * @param items       설문 항목 목록
 */
public record Survey(
    String name,
    String description,
    List<SurveyItem> items
) {

}


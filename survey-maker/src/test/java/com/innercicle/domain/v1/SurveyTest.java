package com.innercicle.domain.v1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("설문 도메인 테스트")
class SurveyTest {

    @Test
    @DisplayName("설문 생성 테스트")
    void create() {
        String name = "설문";
        String description = "설문 설명";
        String subDescription = "질문";
        long id = 1L;
        long subId = 2L;
        Survey survey = Survey.builder()
            .id(id)
            .name(name)
            .description(description)
            .items(List.of(
                SurveyItem.builder()
                    .id(subId)
                    .description(subDescription)
                    .inputType(InputType.SHORT_TEXT)
                    .build()
            ))
            .build();
        assertThat(survey.id()).isEqualTo(id);
        assertThat(survey.name()).isEqualTo(name);
        assertThat(survey.description()).isEqualTo(description);
        assertThat(survey.items()).hasSize(1);
        assertThat(survey.items()).isNotNull();
        assertThat(survey.items().getFirst().id()).isEqualTo(subId);
        assertThat(survey.items().getFirst().description()).isEqualTo(subDescription);
        assertThat(survey.items().getFirst().inputType()).isEqualTo(InputType.SHORT_TEXT);
    }

}
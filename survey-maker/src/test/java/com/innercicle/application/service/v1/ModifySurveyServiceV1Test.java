package com.innercicle.application.service.v1;

import com.innercicle.adapter.in.web.survey.v1.request.ModifySurveyItemRequestV1;
import com.innercicle.adapter.in.web.survey.v1.request.ModifySurveyRequestV1;
import com.innercicle.advice.exceptions.RequiredFieldException;
import com.innercicle.application.port.in.v1.RegisterSurveyCommandV1;
import com.innercicle.application.port.in.v1.RegisterSurveyItemCommandV1;
import com.innercicle.application.port.in.v1.SearchSurveyQueryV1;
import com.innercicle.common.annotations.MockMvcTest;
import com.innercicle.common.container.RedisTestContainer;
import com.innercicle.domain.v1.InputType;
import com.innercicle.domain.v1.Survey;
import com.innercicle.domain.v1.SurveyItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MockMvcTest
@Import({TestcontainersConfiguration.class})
class ModifySurveyServiceV1Test extends RedisTestContainer {

    @Autowired
    private RegisterSurveyServiceV1 registerSurveyServiceV1;

    @Autowired
    private ModifySurveyServiceV1 modifySurveyServiceV1;

    @Autowired
    private SearchSurveyServiceV1 searchSurveyServiceV1;

    private Survey saveDummyData() {
        // given
        RegisterSurveyCommandV1 command = RegisterSurveyCommandV1.builder()
            .name("설문 명")
            .description("설문 설명")
            .items(List.of(
                RegisterSurveyItemCommandV1.builder()
                    .item("항목")
                    .description("항목 설명")
                    .type(InputType.MULTI_SELECT)
                    .required(true)
                    .options(List.of("선택지1", "선택지2"))
                    .build()
            ))
            .build();
        return registerSurveyServiceV1.registerSurvey(command);
    }

    @Test
    @DisplayName("설문 수정 - 성공")
    void modify_success() {
        // given
        Survey survey = saveDummyData();
        // when
        String name = "수정된 설문 명";
        String description = "수정된 설문 설명";
        String item = "수정된 항목";
        String subDescription = "수정된 항목 설명";
        InputType type = InputType.MULTI_SELECT;
        String option1 = "수정된 선택지1";
        String option2 = "수정된 선택지2";
        ModifySurveyRequestV1 request = ModifySurveyRequestV1.builder()
            .id(survey.id())
            .name(name)
            .description(description)
            .items(List.of(
                ModifySurveyItemRequestV1.builder()
                    .id(survey.items().getFirst().id())
                    .item(item)
                    .description(subDescription)
                    .type(type)
                    .required(true)
                    .options(List.of(option1, option2))
                    .build()
            ))
            .build();
        Survey modifiedSurvey = modifySurveyServiceV1.modifySurvey(request.mapToCommand());
        // then
        assertThat(modifiedSurvey.name()).isEqualTo(name);
        assertThat(modifiedSurvey.description()).isEqualTo(description);
        assertThat(modifiedSurvey.items()).hasSize(1);
        assertThat(modifiedSurvey.items().getFirst().item()).isEqualTo(item);
        assertThat(modifiedSurvey.items().getFirst().description()).isEqualTo(subDescription);
        assertThat(modifiedSurvey.items().getFirst().inputType()).isEqualTo(type);
        assertThat(modifiedSurvey.items().getFirst().required()).isTrue();
        assertThat(modifiedSurvey.items().getFirst().options()).containsExactly(option1, option2);

    }

    @Test
    @DisplayName("설문 수정 성공 - 새로운 항목 추가")
    void modify_item_add_new_items() {
        // given
        Survey survey = saveDummyData();
        List<SurveyItem> items = survey.items();
        List<ModifySurveyItemRequestV1> itemsRequests =
            new ArrayList<>(items.stream().map(i -> ModifySurveyItemRequestV1.builder()
                .id(i.id())
                .item(i.item())
                .description(i.description())
                .type(i.inputType())
                .required(i.required())
                .options(i.options())
                .build()).toList());
        String newItem = "새로운 항목";
        String newDescription = "새로운 항목 설명";
        String newOption1 = "새로운 선택지1";
        String newOption2 = "새로운 선택지2";
        itemsRequests.add(
            ModifySurveyItemRequestV1.builder()
                .item(newItem)
                .description(newDescription)
                .type(InputType.SINGLE_SELECT)
                .required(true)
                .options(List.of(newOption1, newOption2))
                .build());
        ModifySurveyRequestV1 request = ModifySurveyRequestV1.builder()
            .id(survey.id())
            .name(survey.name())
            .description(survey.description())
            .items(itemsRequests)
            .build();
        // when
        modifySurveyServiceV1.modifySurvey(request.mapToCommand());
        Survey searchedSurvey = searchSurveyServiceV1.searchSurvey(SearchSurveyQueryV1.of(survey.id()));

        // then
        assertThat(searchedSurvey.items()).hasSize(2);
        assertThat(searchedSurvey.items().getLast().item()).isEqualTo(newItem);
        assertThat(searchedSurvey.items().getLast().description()).isEqualTo(newDescription);
        assertThat(searchedSurvey.items().getLast().inputType()).isEqualTo(InputType.SINGLE_SELECT);
        assertThat(searchedSurvey.items().getLast().required()).isTrue();
        assertThat(searchedSurvey.items().getLast().options()).containsExactly(newOption1, newOption2);

    }

    @Test
    @DisplayName("설문 수정 살퍄 - 기존 항목 삭제 후 항목 하나도 남지 않음")
    void modify_item_remove_items() {
        // given
        Survey survey = saveDummyData();
        List<SurveyItem> items = survey.items();
        // id 가 세팅되지 않았다는 것은 기존 항목이 삭제되었다는 의미
        List<ModifySurveyItemRequestV1> itemsRequests =
            new ArrayList<>(items.stream().map(i -> ModifySurveyItemRequestV1.builder()
                .item(i.item())
                .description(i.description())
                .type(i.inputType())
                .required(i.required())
                .options(i.options())
                .build()).toList());
        // when
        assertThatThrownBy(() -> {
            ModifySurveyRequestV1 request = ModifySurveyRequestV1.builder()
                .id(survey.id())
                .name(survey.name())
                .description(survey.description())
                .items(itemsRequests)
                .build();
            modifySurveyServiceV1.modifySurvey(request.mapToCommand());
        }).isInstanceOf(RequiredFieldException.class)
            .hasMessageContaining("설문 항목은 반드시 하나 이상 존재해야 합니다.")
        ;

        // then

    }

    @Test
    @DisplayName("수정 시 동시성 테스트 - 충돌 없이 순서대로 수정")
    void modify_concurrency() {
        // given
        int threadCount = 10;
        Survey survey = saveDummyData();
        try (ExecutorService executor = Executors.newFixedThreadPool(threadCount)) {
            CountDownLatch latch = new CountDownLatch(threadCount);

            // when
            for (int i = 0; i < threadCount; i++) {
                int finalI = i;
                executor.submit(() -> {
                    try {
                        ModifySurveyRequestV1 request = ModifySurveyRequestV1.builder()
                            .id(survey.id())
                            .name("수정된 설문 명" + finalI)
                            .description("수정된 설문 설명")
                            .items(List.of(
                                ModifySurveyItemRequestV1.builder()
                                    .item("수정된 항목")
                                    .description("수정된 항목 설명")
                                    .type(InputType.MULTI_SELECT)
                                    .required(true)
                                    .options(List.of("수정된 선택지1", "수정된 선택지2"))
                                    .build()
                            ))
                            .build();

                        modifySurveyServiceV1.modifySurvey(request.mapToCommand());
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }
        // then
        Survey searchedSurvey = searchSurveyServiceV1.searchSurvey(SearchSurveyQueryV1.of(survey.id()));
        assertThat(searchedSurvey).isNotNull();
        assertThat(searchedSurvey.name()).isNotEqualTo("수정된 설문 명" + threadCount);
    }

}
package com.innercicle.adapter.in.web.survey.v1;

import com.innercicle.adapter.in.web.survey.v1.request.RegisterSurveyItemRequestV1;
import com.innercicle.adapter.in.web.survey.v1.request.RegisterSurveyRequestV1;
import com.innercicle.advice.exceptions.RequiredFieldException;
import com.innercicle.common.container.AbstractMvcTestContainer;
import com.innercicle.domain.v1.InputType;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegisterSurveyControllerV1Test extends AbstractMvcTestContainer {

    @Test
    @DisplayName("설문 등록 실패 - 필수 필드 누락")
    void register_survey_success() throws Exception {
        String name = "설문";
        String description = "설문 설명";
        RegisterSurveyRequestV1 request = RegisterSurveyRequestV1.builder().name(name).description(description)
            .items(List.of(RegisterSurveyItemRequestV1.builder().item("항목").description("항목 설명")
                               .type(InputType.MULTI_SELECT)
                               .options(List.of(
                                   "선택지1",
                                   "선택지2"))
                               .build()))
            .build();
        this.mockMvc.perform(post("/api/v1/survey").contentType("application/json").content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("response.id").exists())
            .andExpect(jsonPath("response.id").isNumber())
            .andExpect(jsonPath("response.name").value(name))
            .andExpect(jsonPath("response.description").value(description))
            .andExpect(jsonPath("response.items").isArray())
        ;
    }

    @Test
    @DisplayName("설문 등록 실패 - single select 일 경우 선택지 1개 이상이어야 함")
    void register_survey_fail_single_select() throws Exception {
        String name = "설문";
        String description = "설문 설명";
        RegisterSurveyRequestV1 request = RegisterSurveyRequestV1.builder().name(name).description(description)
            .items(List.of(RegisterSurveyItemRequestV1.builder().item("항목").description("항목 설명")
                               .type(InputType.SINGLE_SELECT)
                               .options(List.of(
                                   "선택지1"))
                               .build()))
            .build();
        this.mockMvc.perform(post("/api/v1/survey").contentType("application/json").content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andDo(print())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(result -> {
                Exception resolvedException = result.getResolvedException();
                assertThat(resolvedException).isNotNull();
                assertThat(resolvedException).isInstanceOf(RequiredFieldException.class);
            })
            .andExpect(jsonPath("$.message", containsString("일 경우 선택지는 2개 이상 입력해 주세요.")))
        ;
    }

    @Test
    @DisplayName("설문 등록 실패 - multi select 일 경우 선택지 1개 이상이어야 함")
    void register_survey_fail_multi_select() throws Exception {
        String name = "설문";
        String description = "설문 설명";
        RegisterSurveyRequestV1 request = RegisterSurveyRequestV1.builder().name(name).description(description)
            .items(List.of(RegisterSurveyItemRequestV1.builder().item("항목").description("항목 설명")
                               .type(InputType.MULTI_SELECT)
                               .options(List.of(
                                   "선택지1"))
                               .build()))
            .build();
        this.mockMvc.perform(post("/api/v1/survey").contentType("application/json").content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andDo(print())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(result -> {
                Exception resolvedException = result.getResolvedException();
                assertThat(resolvedException).isNotNull();
                assertThat(resolvedException).isInstanceOf(RequiredFieldException.class);
            })
            .andExpect(jsonPath("$.message", containsString("일 경우 선택지는 2개 이상 입력해 주세요.")))
        ;
    }

    @Test
    @DisplayName("설문 등록 실패 - 필수 필드 누락")
    void register_survey_fail_omit_required_field() throws Exception {
        RegisterSurveyRequestV1 request = RegisterSurveyRequestV1.builder().build();
        this.mockMvc.perform(post("/api/v1/survey").contentType("application/json").content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError())
            .andDo(print())
            .andExpect(result -> {
                Exception resolvedException = result.getResolvedException();
                assertThat(resolvedException).isNotNull();
                assertThat(resolvedException).isInstanceOf(ConstraintViolationException.class);
            })
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.message", containsString("설문 명은 필수 입니다.")))
            .andExpect(jsonPath("$.message", containsString("설문 설명은 필수 입니다.")))
            .andExpect(jsonPath("$.message", containsString("설문 설명은 필수 입니다.")))
            .andExpect(jsonPath("$.message", containsString("설문 항목 목록은 필수 입니다.")))
        ;
    }

    @Test
    @DisplayName("설문 등록 실패 - 아이템 항목 누락")
    void register_survey_fail_omission_items() throws Exception {
        RegisterSurveyRequestV1 request = RegisterSurveyRequestV1.builder()
            .name("테스트 설문")
            .description("테스트 설문 설명")
            .build();
        this.mockMvc.perform(post("/api/v1/survey").contentType("application/json").content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError())
            .andDo(print())
            .andExpect(result -> {
                Exception resolvedException = result.getResolvedException();
                assertThat(resolvedException).isNotNull();
                assertThat(resolvedException).isInstanceOf(ConstraintViolationException.class);
            })
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.message", not(containsString("설문 명은 필수 입니다."))))
            .andExpect(jsonPath("$.message", not(containsString("설문 설명은 필수 입니다."))))
            .andExpect(jsonPath("$.message", not(containsString("설문 설명은 필수 입니다."))))
            .andExpect(jsonPath("$.message", containsString("설문 항목 목록은 필수 입니다.")))
        ;
    }

    @Test
    @DisplayName("설문 등록 실패 - 아이템 목록 필수 항목 누락")
    void register_survey_fail_omission_items_required_field() throws Exception {
        RegisterSurveyRequestV1 request = RegisterSurveyRequestV1.builder()
            .name("테스트 설문")
            .description("테스트 설문 설명")
            .items(List.of(RegisterSurveyItemRequestV1.builder().build()))
            .build();
        this.mockMvc.perform(post("/api/v1/survey").contentType("application/json").content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError())
            .andDo(print())
            .andExpect(result -> {
                Exception resolvedException = result.getResolvedException();
                assertThat(resolvedException).isNotNull();
                assertThat(resolvedException).isInstanceOf(ConstraintViolationException.class);
            })
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.message", containsString("설문 항목 명은 필수 입니다.")))
            .andExpect(jsonPath("$.message", containsString("설문 항목 설명은 필수 입니다.")))
            .andExpect(jsonPath("$.message", containsString("설문 항목 타입은 필수 입니다.")))
            .andExpect(jsonPath("$.message", containsString("설문 항목 선택지 목록은 필수 입니다.")))
        ;
    }

}
package com.innercircle.command.interfaces;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class SurveyControllerTest {

	private final WebApplicationContext context;

	private MockMvc mockMvc;

	@Autowired
	public SurveyControllerTest(WebApplicationContext context) {
		this.context = context;
	}

	@Test
	@DisplayName("설문조사 생성 성공 테스트 (설문 항목을 올바르게 입력한 경우)")
	void surveyCreationSuccessTest() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		mockMvc.perform(post("/c/create-survey")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "name": "설문조사 이름",
								  "description": "설문조사 설명",
								  "questionInputs": [
								    {
								      "type": "SINGLE_CHOICE",
								      "name": "단일 선택 리스트 설문조사 이름",
								      "description": "단일 선택 리스트 설문조사 설명",
								      "optionNames": [
								        "option1"
								      ],
								      "required": true
								    },
								    {
								      "type": "MULTIPLE_CHOICE",
								      "name": "다중 선택 리스트 설문조사 이름",
								      "description": "다중 선택 리스트 설문조사 설명",
								      "optionNames": [
								        "option1",
								        "option2",
								        "option3"
								      ],
								      "required": true
								    },
								    {
								      "type": "SHORT_TEXT",
								      "name": "단답형 설문조사 이름",
								      "description": "단답형 설문조사 설명",
								      "required": true
								    },
								    {
								      "type": "LONG_TEXT",
								      "name": "장문형 설문조사 이름",
								      "description": "장문형 설문조사 설명",
								      "required": true
								    }
								  ]
								}
								""")
				)
				.andExpect(status().isOk())
				.andExpect(content().string(not(emptyOrNullString())));
	}

	@Test
	@DisplayName("설문조사 생성 실패 테스트 (설문 조사 이름이 없는 경우)")
	void surveyCreationFailureTest1() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		mockMvc.perform(post("/c/create-survey")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "description": "설문조사 설명"
								}
								""")
				)
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Survey name or description must not be empty"));
	}

	@Test
	@DisplayName("설문조사 생성 실패 테스트 (설문 조사 설명이 없는 경우)")
	void surveyCreationFailureTest2() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		mockMvc.perform(post("/c/create-survey")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "name": "설문조사 이름"
								}
								""")
				)
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Survey name or description must not be empty"));
	}

	@Test
	@DisplayName("설문조사 생성 실패 테스트 (설문 받을 항목이 없는 경우)")
	void surveyCreationFailureTest3() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		mockMvc.perform(post("/c/create-survey")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "name": "설문조사 이름",
								  "description": "설문조사 설명"
								}
								""")
				)
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Survey must have between 1 and 10 questions"));
	}

	@Test
	@DisplayName("설문조사 생성 실패 테스트 (단답형 설문 항목 이름이 없는 경우)")
	void surveyCreationFailureTest4() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		mockMvc.perform(post("/c/create-survey")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "name": "설문조사 이름",
								  "description": "설문조사 설명",
								  "questionInputs": [
								    {
								      "type": "SHORT_TEXT",
								      "description": "단답형 설문조사 설명",
								      "required": true
								    }
								  ]
								}
								""")
				)
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Question name must not be empty and must not exceed 20 characters"));
	}

	@Test
	@DisplayName("설문조사 생성 실패 테스트 (단답형 설문 항목 이름이 최대 글자를 초과하는 경우)")
	void surveyCreationFailureTest5() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		mockMvc.perform(post("/c/create-survey")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "name": "설문조사 이름",
								  "description": "설문조사 설명",
								  "questionInputs": [
								    {
								      "type": "SHORT_TEXT",
								      "name": "123456789012345678901",
								      "description": "단답형 설문조사 설명",
								      "required": true
								    }
								  ]
								}
								""")
				)
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Question name must not be empty and must not exceed 20 characters"));
	}

	@Test
	@DisplayName("설문조사 생성 실패 테스트 (단답형 설문 항목 설명이 없는 경우)")
	void surveyCreationFailureTest6() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		mockMvc.perform(post("/c/create-survey")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "name": "설문조사 이름",
								  "description": "설문조사 설명",
								  "questionInputs": [
								    {
								      "type": "SHORT_TEXT",
								      "name": "단답형 설문조사 이름",
								      "required": true
								    }
								  ]
								}
								""")
				)
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Question description must not be empty and must not exceed 30 characters"));
	}

	@Test
	@DisplayName("설문조사 생성 실패 테스트 (단답형 설문 항목 설명이 최대 글자를 초과하는 경우)")
	void surveyCreationFailureTest7() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		mockMvc.perform(post("/c/create-survey")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "name": "설문조사 이름",
								  "description": "설문조사 설명",
								  "questionInputs": [
								    {
								      "type": "SHORT_TEXT",
								      "name": "단답형 설문조사 이름",
								      "description": "1234567890123456789012345678901",
								      "required": true
								    }
								  ]
								}
								""")
				)
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Question description must not be empty and must not exceed 30 characters"));
	}
}

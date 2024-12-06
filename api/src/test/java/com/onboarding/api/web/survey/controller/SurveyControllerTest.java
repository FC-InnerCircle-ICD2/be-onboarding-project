package com.onboarding.api.web.survey.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onboarding.api.web.survey.dto.request.QuestionCreateReqDto;
import com.onboarding.api.web.survey.dto.request.SurveyCreateReqDto;
import com.onboarding.survey.enums.QuestionType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SurveyControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void createSurvey_ShouldReturn200() throws Exception {
    // Given
    SurveyCreateReqDto request = new SurveyCreateReqDto(
        "Customer Feedback",
        "A survey to gather feedback",
        List.of(
            new QuestionCreateReqDto(
                "How satisfied are you?",
                "Rate from 1 to 5",
                QuestionType.SINGLE_CHOICE,
                true,
                false,
                List.of("1", "2", "3", "4", "5")
            )
        )
    );

    // When & Then
    mockMvc.perform(post("/api/v1/surveys")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Success"));
  }

  @Test
  void getSurvey_ShouldReturnSurveyWithQuestions() throws Exception {
    // Given
    Long surveyId = 1L;

    // When & Then
    mockMvc.perform(get("/api/v1/surveys/{surveyId}", surveyId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.name").value("Customer Feedback"));
  }
}
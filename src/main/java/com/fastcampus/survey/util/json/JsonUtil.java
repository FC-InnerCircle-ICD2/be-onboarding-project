package com.fastcampus.survey.util.json;

import com.fastcampus.survey.entity.Survey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String survey2Json(Survey survey) {
        try {
            return objectMapper.writeValueAsString(
                    new SurveyDetails(survey.getName(), survey.getDescription())
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Survey to JSON", e);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SurveyDetails {
        private String name;
        private String description;
    }
}

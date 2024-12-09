package com.innercircle.surveryproject.modules.dto.validators;

import com.innercircle.surveryproject.global.utils.FileUtils;
import com.innercircle.surveryproject.modules.dto.SurveyCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class SurveyCreateDtoValidatorTest {

    private SurveyCreateDtoValidator validator;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        validator = new SurveyCreateDtoValidator();
    }

    @Test
    void whenInvalidDto_thenValidationFails() throws IOException {
        // Invalid DTO (title is null)
        JsonNode jsonNode = FileUtils.readFileAsJson("testcase/fail_survey.txt");
        SurveyCreateDto invalidDto = objectMapper.treeToValue(jsonNode, SurveyCreateDto.class);

        Errors errors = new BeanPropertyBindingResult(invalidDto, "surveyCreateDto");

        validator.validate(invalidDto, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(Objects.requireNonNull(errors.getFieldError("surveyItemDtoList")).getDefaultMessage()).isEqualTo(
            "설문조사 항목은 1~10개 까지 입력 가능합니다.");
    }

    @Test
    void whenValidDto_thenValidationPasses() throws IOException {
        // Valid DTO
        JsonNode jsonNode = FileUtils.readFileAsJson("testcase/success_survey.txt");
        SurveyCreateDto validDto = objectMapper.treeToValue(jsonNode, SurveyCreateDto.class);

        Errors errors = new BeanPropertyBindingResult(validDto, "surveyCreateDto");

        validator.validate(validDto, errors);

        assertThat(errors.hasErrors()).isFalse();
    }

}
package com.metsakurr.beonboardingproject.domain.survey.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import com.metsakurr.beonboardingproject.common.exception.ServiceException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Converter
@RequiredArgsConstructor
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(List<String> dataList) {
        try {
            return mapper.writeValueAsString(dataList);
        } catch (JsonProcessingException ex) {
            throw new ServiceException(ResponseCode.FAIL_STRING_TO_JSON);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String data) {
        try {
            return mapper.readValue(data, List.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}

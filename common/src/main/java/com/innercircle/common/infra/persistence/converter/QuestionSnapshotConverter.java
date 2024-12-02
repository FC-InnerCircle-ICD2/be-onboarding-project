package com.innercircle.common.infra.persistence.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innercircle.common.domain.survey.question.QuestionSnapshot;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class QuestionSnapshotConverter implements AttributeConverter<QuestionSnapshot, String> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(QuestionSnapshot attribute) {
		if (attribute == null) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Error serializing AnswerContent to JSON", e);
		}
	}

	@Override
	public QuestionSnapshot convertToEntityAttribute(String dbData) {
		if (dbData == null || dbData.isEmpty()) {
			return null;
		}
		try {
			return objectMapper.readValue(dbData, QuestionSnapshot.class);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Error deserializing JSON to AnswerContent", e);
		}
	}
}

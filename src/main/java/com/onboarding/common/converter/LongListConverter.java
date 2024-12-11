package com.onboarding.common.converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;

import org.springframework.util.CollectionUtils;

public class LongListConverter implements AttributeConverter<List<String>, String> {

	private static final String DELIMITER = ",";

	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		if (CollectionUtils.isEmpty(attribute)) {
			return null;
		}
		return String.join(DELIMITER, attribute);
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		if (dbData.isEmpty()) {
			return List.of();
		}
		return Arrays.stream(dbData.split(DELIMITER))
			.map(String::trim)
			.collect(Collectors.toList());
	}
}

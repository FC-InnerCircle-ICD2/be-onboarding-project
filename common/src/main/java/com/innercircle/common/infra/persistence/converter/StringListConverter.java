package com.innercircle.common.infra.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

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
		if (StringUtils.isBlank(dbData)) {
			return List.of();
		}
		return Arrays.stream(dbData.split(DELIMITER))
				.map(String::trim)
				.toList();
	}
}

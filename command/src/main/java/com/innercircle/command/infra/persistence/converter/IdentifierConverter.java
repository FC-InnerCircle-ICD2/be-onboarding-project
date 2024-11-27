package com.innercircle.command.infra.persistence.converter;

import com.innercircle.command.domain.Identifier;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IdentifierConverter implements AttributeConverter<Identifier, String> {

	@Override
	public String convertToDatabaseColumn(Identifier identifier) {
		return identifier != null ? identifier.getId() : null;
	}

	@Override
	public Identifier convertToEntityAttribute(String dbData) {
		return dbData != null ? new Identifier(dbData) : null;
	}
}

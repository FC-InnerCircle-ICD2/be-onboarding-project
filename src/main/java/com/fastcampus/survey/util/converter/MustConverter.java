package com.fastcampus.survey.util.converter;

import com.fastcampus.survey.util.constant.Must;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MustConverter implements AttributeConverter<Must, String> {
    @Override
    public String convertToDatabaseColumn(Must attribute) {
        return attribute.getFlg();
    }

    @Override
    public Must convertToEntityAttribute(String dbData) {
        try {
            return Must.fromValue(dbData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

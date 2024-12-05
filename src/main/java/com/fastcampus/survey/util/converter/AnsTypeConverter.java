package com.fastcampus.survey.util.converter;

import com.fastcampus.survey.util.constant.AnsType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AnsTypeConverter implements AttributeConverter<AnsType, String> {
    @Override
    public String convertToDatabaseColumn(AnsType attribute) {
        return attribute.getAnsType();
    }

    @Override
    public AnsType convertToEntityAttribute(String dbData) {
        try {
            return AnsType.fromValue(dbData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

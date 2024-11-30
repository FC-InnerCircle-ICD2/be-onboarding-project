package net.gentledot.survey.model.entity.common;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import net.gentledot.survey.exception.ServiceError;
import net.gentledot.survey.exception.SurveySubmitValidationException;
import net.gentledot.survey.model.enums.AnswerType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Converter
public class AnswerConverter implements AttributeConverter<Object, String> {
    @Override
    public String convertToDatabaseColumn(Object attribute) {
        if (attribute == null) {
            return null;
        }

        return switch (attribute) {
            case String stringAttribute -> AnswerType.TEXT.convertToDatabaseData(stringAttribute);
            case LocalDate localDateAttribute -> AnswerType.DATE.convertToDatabaseData(localDateAttribute);
            case LocalDateTime localDateTimeAttribute ->
                    AnswerType.DATE_TIME.convertToDatabaseData(localDateTimeAttribute);
            case byte[] bytesAttribute -> AnswerType.FILE.convertToDatabaseData(bytesAttribute);
            case null, default -> throw new SurveySubmitValidationException(ServiceError.SUBMIT_UNSUPPORTED_ATTRIBUTE);
        };
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        if (dbData.startsWith(AnswerType.TEXT.getFlag())) {
            return AnswerType.TEXT.getConvertToEntity(dbData);
        } else if (dbData.startsWith(AnswerType.DATE.getFlag())) {
            return AnswerType.DATE.getConvertToEntity(dbData);
        } else if (dbData.startsWith(AnswerType.DATE_TIME.getFlag())) {
            return AnswerType.DATE_TIME.getConvertToEntity(dbData);
        } else if (dbData.startsWith(AnswerType.FILE.getFlag())) {
            return AnswerType.FILE.getConvertToEntity(dbData);
        }
        throw new SurveySubmitValidationException(ServiceError.SUBMIT_DATA_CONVERT_ERROR);
    }
}

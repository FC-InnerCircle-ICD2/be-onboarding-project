package net.gentledot.survey.model.enums;

import lombok.ToString;
import net.gentledot.survey.model.DateTimeFormatUtility;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.function.Function;

@ToString
public enum AnswerType {
    TEXT("TEXT:", attribute -> new StringBuilder("TEXT:").append(attribute).toString(), dbData -> StringUtils.removeStart(dbData, "TEXT:")),
    DATE("DATE:", attribute -> new StringBuilder("DATE:").append(attribute).toString(), dbData -> LocalDate.parse(StringUtils.removeStart(dbData, "DATE:"), DateTimeFormatUtility.DEFAULT_DATE_FORMATTER)),
    DATE_TIME("TIME:", attribute -> new StringBuilder("TIME:").append(attribute).toString(), dbData -> LocalDateTime.parse(StringUtils.removeStart(dbData, "TIME:"), DateTimeFormatUtility.DEFAULT_DATE_TIME_FORMATTER)),
    FILE("FILE:", attribute -> new StringBuilder("FILE:").append(Base64.getEncoder().encodeToString((byte[]) attribute)).toString(), dbData -> Base64.getDecoder().decode(StringUtils.removeStart(dbData, "FILE:"))),
    ;

    private String flag;
    private Function<Object, String> convertToDatabase;
    private Function<String, Object> convertToEntity;

    AnswerType(String flag, Function<Object, String> convertToDatabase, Function<String, Object> convertToEntity) {
        this.flag = flag;
        this.convertToDatabase = convertToDatabase;
        this.convertToEntity = convertToEntity;
    }

    public String getFlag() {
        return flag;
    }

    public String convertToDatabaseData(Object attribute) {
        return convertToDatabase.apply(attribute);
    }

    public Object getConvertToEntity(String dbData) {
        return convertToEntity.apply(dbData);
    }
}

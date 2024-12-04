package net.gentledot.survey.dto.enums;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum UpdateType {
    MODIFY("수정 또는 추가"),
    DELETE("삭제");

    private final String description;

    UpdateType(String description) {
        this.description = description;
    }
}

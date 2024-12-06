package net.gentledot.survey.domain.enums;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum UpdateType {
    ADD("입력"),
    MODIFY("수정"),
    DELETE("삭제");

    private final String description;

    UpdateType(String description) {
        this.description = description;
    }
}

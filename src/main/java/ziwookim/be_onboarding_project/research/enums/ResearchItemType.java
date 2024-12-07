package ziwookim.be_onboarding_project.research.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ziwookim.be_onboarding_project.common.web.enums.HttpErrors;
import ziwookim.be_onboarding_project.common.web.exception.BadRequestException;

@RequiredArgsConstructor
@Getter
public enum ResearchItemType {

    SHORT_ANSWER(1), // 단답형
    LONG_SENTENCE(2), // 장문형
    SINGLE_SELECTION(3), // 단일 선택형
    MULTIPLE_SELECTION(4); // 다중 선택형

    private final int itemType;

    public static boolean isValidItemType(int itemType) {
        for (ResearchItemType type : ResearchItemType.values()) {
            if (type.getItemType() == itemType) {
                return true;
            }
        }
        return false;
    }

    public static String getResearchItemTypeName(int itemType) {
        if(!ResearchItemType.isValidItemType(itemType)) {
            throw BadRequestException.of(HttpErrors.ITEM_TYPE_NAME_NOT_FOUND);
        }

        for (ResearchItemType type : ResearchItemType.values()) {
            if (type.getItemType() == itemType) {
                return type.name();
            }
        }
        throw BadRequestException.of(HttpErrors.ITEM_TYPE_NAME_NOT_FOUND);
    }
}

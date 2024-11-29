package ziwookim.be_onboarding_project.research.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ziwookim.be_onboarding_project.research.enums.ResearchItemType;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
public class ResearchItemVo {
    private Long id;
    private String name;
    private String description;
    private Integer itemType;
    private String itemTypeName;
    private Boolean isRequired;
    private List<ResearchItemChoiceVo> itemChoiceList;

    public static ResearchItemVo of(
            Long id,
            String name,
            String description,
            int itemType,
            String itemTypeName,
            boolean isRequired,
            List<ResearchItemChoiceVo> itemChoiceList
    ) {
        return ResearchItemVo.builder()
                .id(id)
                .name(name)
                .description(description)
                .itemType(itemType)
                .itemTypeName(itemTypeName)
                .isRequired(isRequired)
                .itemChoiceList(itemChoiceList)
                .build();
    }

}

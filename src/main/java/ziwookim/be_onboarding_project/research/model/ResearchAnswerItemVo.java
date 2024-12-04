package ziwookim.be_onboarding_project.research.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
public class ResearchAnswerItemVo {
    private Long id;
    private String name;
    private String description;
    private Integer itemType;
    private String itemTypeName;
    private Boolean isRequired;
    private List<ResearchItemChoiceVo> itemChoiceList;
    private Object answer;

    public static ResearchAnswerItemVo of(
            Long id,
            String name,
            String description,
            int itemType,
            String itemTypeName,
            boolean isRequired,
            List<ResearchItemChoiceVo> itemChoiceList,
            Object answer
    ) {
        return ResearchAnswerItemVo.builder()
                .id(id)
                .name(name)
                .description(description)
                .itemType(itemType)
                .itemTypeName(itemTypeName)
                .isRequired(isRequired)
                .itemChoiceList(itemChoiceList)
                .answer(answer)
                .build();
    }

}

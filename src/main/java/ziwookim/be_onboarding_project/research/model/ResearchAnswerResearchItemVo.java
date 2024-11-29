package ziwookim.be_onboarding_project.research.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
public class ResearchAnswerResearchItemVo {
    private Long id;
    private String name;
    private String description;
    private Integer itemType;
    private String itemTypeName;
    private Boolean isRequired;
    private List<ResearchItemChoiceVo> itemChoiceList;
    private Object answer;

    public static ResearchAnswerResearchItemVo of(
            Long id,
            String name,
            String description,
            Integer itemType,
            String itemTypeName,
            Boolean isRequired,
            List<ResearchItemChoiceVo> itemChoiceList,
            Object answer
    ) {
        return ResearchAnswerResearchItemVo.builder()
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

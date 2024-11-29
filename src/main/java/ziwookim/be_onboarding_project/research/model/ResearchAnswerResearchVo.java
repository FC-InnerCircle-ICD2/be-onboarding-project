package ziwookim.be_onboarding_project.research.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
public class ResearchAnswerResearchVo {
    private Long id;
    private String title;
    private String description;
    private List<ResearchAnswerResearchItemVo> itemList;

    public static ResearchAnswerResearchVo of(
            Long id,
            String title,
            String description,
            List<ResearchAnswerResearchItemVo> itemList
    ) {
        return ResearchAnswerResearchVo.builder()
                .id(id)
                .title(title)
                .description(description)
                .itemList(itemList)
                .build();
    }
}

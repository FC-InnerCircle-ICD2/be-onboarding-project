package ziwookim.be_onboarding_project.research.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
public class ResearchVo {
    private Long id;
    private String title;
    private String description;
    private List<ResearchItemVo> researchItemVoList;

    public static ResearchVo of (
            Long id,
            String title,
            String description,
            List<ResearchItemVo> researchItemVoList
    ) {
        return ResearchVo.builder()
                .id(id)
                .title(title)
                .description(description)
                .researchItemVoList(researchItemVoList)
                .build();
    }
}

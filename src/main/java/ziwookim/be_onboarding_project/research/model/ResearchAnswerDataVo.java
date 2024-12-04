package ziwookim.be_onboarding_project.research.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
public class ResearchAnswerDataVo {
    private Long id;
    private String title;
    private String description;
    private List<ResearchAnswerItemVo> researchAnswerItemVoList;

    public static ResearchAnswerDataVo of (
            Long id,
            String title,
            String description,
            List<ResearchAnswerItemVo> researchAnswerItemVoList
    ) {
        return ResearchAnswerDataVo.builder()
                .id(id)
                .title(title)
                .description(description)
                .researchAnswerItemVoList(researchAnswerItemVoList)
                .build();
    }
}

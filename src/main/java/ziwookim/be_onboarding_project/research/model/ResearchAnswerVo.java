package ziwookim.be_onboarding_project.research.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ziwookim.be_onboarding_project.research.entity.Research;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
public class ResearchAnswerVo {
    private ResearchAnswerResearchVo research;

    public static ResearchAnswerVo of(
            ResearchAnswerResearchVo research
    ) {
        return ResearchAnswerVo.builder()
                .research(research)
                .build();
    }
}

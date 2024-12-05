package ziwookim.be_onboarding_project.research.model;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
public class ResearchAnswerListVo {
    private List<ResearchAnswerVo> voList;

    public static ResearchAnswerListVo of(
            List<ResearchAnswerVo> voList
    ) {
        return ResearchAnswerListVo.builder()
                .voList(voList)
                .build();
    }
}

package ziwookim.be_onboarding_project.research.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
public class ResearchItemChoiceVo {
    private Long id;
    private String content;

    public static ResearchItemChoiceVo of(
            Long id,
            String content
    ) {
        return ResearchItemChoiceVo.builder()
                .id(id)
                .content(content)
                .build();
    }

}

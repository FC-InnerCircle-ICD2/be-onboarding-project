package ziwookim.be_onboarding_project.research.model;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
public class ResearchAnswerVo {
    private Long id;
    private ResearchAnswerDataVo data;

    public static ResearchAnswerVo of(
            Long id,
            ResearchAnswerDataVo data
    ) {
        return ResearchAnswerVo.builder()
                .id(id)
                .data(data)
                .build();
    }
}

package ziwookim.be_onboarding_project.research.dto.response;

import lombok.*;
import ziwookim.be_onboarding_project.research.model.ResearchItemChoiceVo;
import ziwookim.be_onboarding_project.research.model.ResearchVo;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchItemChoiceResponse {
    private Long researchItemChoiceId;
    private String content;

    public static ResearchItemChoiceResponse of(ResearchItemChoiceVo vo) {
        return ResearchItemChoiceResponse.builder()
                .researchItemChoiceId(vo.getId())
                .content(vo.getContent())
                .build();
    }
}

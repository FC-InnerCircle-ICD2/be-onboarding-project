package ziwookim.be_onboarding_project.research.dto.response;

import lombok.*;
import ziwookim.be_onboarding_project.research.entity.ResearchAnswer;
import ziwookim.be_onboarding_project.research.model.ResearchAnswerVo;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchAnswerResponse {
    private Long researchAnswerId;
    private ResearchAnswerDataResponse data;

    public static ResearchAnswerResponse of(ResearchAnswerVo vo) {
        return ResearchAnswerResponse.builder()
                .researchAnswerId(vo.getId())
                .data(ResearchAnswerDataResponse.of(vo.getData()))
                .build();
    }
}

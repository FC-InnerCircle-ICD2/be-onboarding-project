package ziwookim.be_onboarding_project.research.dto.response;


import lombok.*;
import ziwookim.be_onboarding_project.research.model.ResearchAnswerListVo;
import ziwookim.be_onboarding_project.research.model.ResearchAnswerVo;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchAnswerListResponse {
    private List<ResearchAnswerResponse> researchAnswerList;

    public static ResearchAnswerListResponse of(ResearchAnswerListVo vo) {
        return ResearchAnswerListResponse.builder()
                .researchAnswerList(vo.getVoList().stream().map(ResearchAnswerResponse::of).toList())
                .build();
    }
}

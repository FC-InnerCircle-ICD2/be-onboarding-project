package ziwookim.be_onboarding_project.research.dto.response;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ziwookim.be_onboarding_project.research.model.ResearchAnswerListVo;

import java.util.List;

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

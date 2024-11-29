package ziwookim.be_onboarding_project.research.dto.response;

import lombok.*;
import ziwookim.be_onboarding_project.research.model.ResearchItemVo;
import ziwookim.be_onboarding_project.research.model.ResearchVo;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchResponse {
    private Long id;
    private String title;
    private String description;
    private List<ResearchItemResponse> researchItemResponseList;

    public static ResearchResponse of(ResearchVo vo) {
        return ResearchResponse.builder()
                .id(vo.getId())
                .title(vo.getTitle())
                .description(vo.getDescription())
                .researchItemResponseList(vo.getResearchItemVoList().stream().map(ResearchItemResponse::of).toList())
                .build();
    }
}

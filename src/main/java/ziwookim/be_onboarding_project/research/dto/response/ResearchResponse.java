package ziwookim.be_onboarding_project.research.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ziwookim.be_onboarding_project.research.model.ResearchVo;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchResponse {
    private Long researchId;
    private String title;
    private String description;
    private List<ResearchItemResponse> researchItemResponseList;

    public static ResearchResponse of(ResearchVo vo) {
        return ResearchResponse.builder()
                .researchId(vo.getId())
                .title(vo.getTitle())
                .description(vo.getDescription())
                .researchItemResponseList(vo.getResearchItemVoList().stream().map(ResearchItemResponse::of).toList())
                .build();
    }
}

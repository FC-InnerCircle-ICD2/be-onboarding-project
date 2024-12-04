package ziwookim.be_onboarding_project.research.dto.response;

import lombok.*;
import ziwookim.be_onboarding_project.research.model.ResearchAnswerDataVo;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchAnswerDataResponse {
    private Long researchId;
    private String title;
    private String description;
    private List<ResearchAnswerItemResponse> researchAnswerItemResponseList;

    public static ResearchAnswerDataResponse of(ResearchAnswerDataVo vo) {
        return ResearchAnswerDataResponse.builder()
                .researchId(vo.getId())
                .title(vo.getTitle())
                .description(vo.getDescription())
                .researchAnswerItemResponseList(vo.getResearchAnswerItemVoList().stream().map(ResearchAnswerItemResponse::of).toList())
                .build();
    }
}

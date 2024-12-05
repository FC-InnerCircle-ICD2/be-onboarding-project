package ziwookim.be_onboarding_project.research.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "설문조사 등록 항목 선택 request")
public class ResearchItemChoiceRequestVo {

    @NotBlank(message="설문조사 항목 선택 값")
    @Schema(description = "ResearchItemChoice content", example = "choice_content")
    private String content;
}

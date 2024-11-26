package ziwookim.be_onboarding_project.dto.request;

import lombok.*;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchAnswerRequestVo {

    private String response;
}

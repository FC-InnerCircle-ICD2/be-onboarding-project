package ziwookim.be_onboarding_project.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
    protected final String code;
    protected final String message;

    public CommonResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

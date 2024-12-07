package ziwookim.be_onboarding_project.common.response;

import lombok.Getter;
import lombok.Setter;
import ziwookim.be_onboarding_project.common.web.error.HttpError;
import ziwookim.be_onboarding_project.common.web.enums.HttpErrors;

@Getter
@Setter
public class BadRequestResponse extends CommonResponse {
    private final static HttpError defaultBadRequestError = HttpErrors.BAD_REQUEST;

    public BadRequestResponse() {
        super(defaultBadRequestError.getCode(), defaultBadRequestError.getMessage());
    }

    public BadRequestResponse(HttpError error) {
        super(error.getCode(), error.getMessage());
    }

    public static BadRequestResponse of(HttpError error) {
        return new BadRequestResponse(error);
    }

    public static BadRequestResponse of() {
        return new BadRequestResponse();
    }
}

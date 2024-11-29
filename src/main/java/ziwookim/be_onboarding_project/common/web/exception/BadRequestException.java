package ziwookim.be_onboarding_project.common.web.exception;

import lombok.*;
import ziwookim.be_onboarding_project.common.web.error.HttpError;
import ziwookim.be_onboarding_project.common.web.enums.HttpErrors;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BadRequestException extends IllegalArgumentException {
    private final HttpError error;

    public static BadRequestException of() {
        return BadRequestException.builder()
                .error(HttpErrors.BAD_REQUEST)
                .build();
    }

    public static BadRequestException of(HttpError error) {
        return BadRequestException.builder()
                .error(error)
                .build();
    }

    public BadRequestException(
            String message,
            HttpError error) {
        super(message);
        this.error = error;
    }

    public static BadRequestException of(
            HttpError error,
            String message) {
        return new BadRequestException(message, error);
    }
}

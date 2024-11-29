package ziwookim.be_onboarding_project.common.web.exception;

import lombok.*;
import ziwookim.be_onboarding_project.common.web.enums.HttpErrors;
import ziwookim.be_onboarding_project.common.web.error.HttpError;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class NotFoundException extends IllegalArgumentException {
    private final HttpError error;

    public static NotFoundException of() {
        return NotFoundException.builder()
                .error(HttpErrors.BAD_REQUEST)
                .build();
    }

    public static NotFoundException of(HttpError error) {
        return NotFoundException.builder()
                .error(error)
                .build();
    }
}
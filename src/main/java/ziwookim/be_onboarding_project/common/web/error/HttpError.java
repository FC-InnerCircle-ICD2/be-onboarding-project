package ziwookim.be_onboarding_project.common.web.error;

import org.springframework.http.HttpStatus;

public interface HttpError {
    HttpStatus getStatus();
    String getCode();
    String getMessage();
}

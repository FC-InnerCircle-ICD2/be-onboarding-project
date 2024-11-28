package com.onboarding.response.object;

import java.util.List;

public record ResponseObject(
    Long surveyId,
    String email,
    List<ResponseDetailObject> responseDetails
) {

}

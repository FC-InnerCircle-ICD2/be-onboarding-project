package org.innercircle.surveyapiapplication.domain.surveyItem.application;

import java.util.UUID;

public class SurveyItemIdGenerator {

    public static Long generateId() {
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }

}

package org.innercircle.surveyapiapplication.domain.survey.application;

import java.util.UUID;

public class QuestionIdGenerator {

    public static Long generateId() {
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }

}

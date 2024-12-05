package org.survey.api.common.error;

public interface ErrorCode {

    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getDescription();
}

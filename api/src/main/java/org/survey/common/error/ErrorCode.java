package org.survey.common.error;

public interface ErrorCode {

    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getDescription();
}

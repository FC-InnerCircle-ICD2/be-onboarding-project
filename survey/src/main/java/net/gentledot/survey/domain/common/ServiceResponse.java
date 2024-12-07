package net.gentledot.survey.domain.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.gentledot.survey.domain.exception.Error;
import net.gentledot.survey.domain.exception.ServiceError;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceResponse<T> {
    private final boolean success;
    private final T data;
    private final net.gentledot.survey.domain.exception.Error error;

    public static <T> ServiceResponse<T> success(T data) {
        return new ServiceResponse<>(true, data, null);
    }

    public static <T> ServiceResponse<T> fail(ServiceError error) {
        return new ServiceResponse<>(false, null, new Error(error));
    }
}

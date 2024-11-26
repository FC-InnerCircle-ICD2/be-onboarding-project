package net.gentledot.survey.dto.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.gentledot.survey.exception.ServiceError;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceResponse<T> {
    private final boolean success;
    private final T data;
    private final Error error;

    public static <T> ServiceResponse<T> success(T data) {
        return new ServiceResponse<>(true, data, null);
    }

    public static <T> ServiceResponse<T> fail(ServiceError error) {
        return new ServiceResponse<>(false, null, new Error(error));
    }
}

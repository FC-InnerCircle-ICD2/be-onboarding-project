package org.survey.common.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.survey.common.error.ErrorCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    @Valid
    private T body;

    public static <T> Api<T> OK(T data){
        var api = new Api<T>();
        api.result = Result.OK();
        api.body = data;
        return  api;
    }

    public static Api<Object> ERROR(Result result){
        var api = new Api<Object>();
        api.result = result;
        return  api;
    }

    public static Api<Object> ERROR(ErrorCode errorCode){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCode);
        return  api;
    }

    public static Api<Object> ERROR(ErrorCode errorCode, Throwable tx){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCode, tx);
        return  api;
    }

    public static Api<Object> ERROR(ErrorCode errorCode, String description){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCode, description);
        return  api;
    }
}

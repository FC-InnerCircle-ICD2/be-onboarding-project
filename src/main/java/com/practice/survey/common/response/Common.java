package com.practice.survey.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Common {

    private int code;

    private String message;

    @JsonIgnore
    public Common responseOk(){
        return this.setCode(StatusEnum.SUCCESS.getCode())
                .setMessage(StatusEnum.SUCCESS.getMessage())
                ;
    }

    @JsonIgnore
    public Common serverError(StatusEnum status){
        return this.setCode(status.getCode())
                .setMessage(status.getMessage())
                ;
    }

    @JsonIgnore
    public Common serverError(int code, String message){
        return this.setCode(code)
                .setMessage(message)
                ;
    }
}

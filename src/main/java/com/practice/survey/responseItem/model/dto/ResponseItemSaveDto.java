package com.practice.survey.responseItem.model.dto;

import com.practice.survey.response.model.entity.Response;
import com.practice.survey.responseItem.model.entity.ResponseItem;
import com.practice.survey.surveyItem.model.entity.SurveyItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseItemSaveDto {

    private SurveyItem item;

    private Response response;

    private String responseValue;

    public ResponseItem toEntity(){
        return ResponseItem.builder()
                .item(item)
                .response(response)
                .responseValue(responseValue)
                .build();
    }
}

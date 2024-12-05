package com.survey.api.util;

import com.survey.api.entity.SurveyResponseEntity;
import com.survey.api.entity.SurveyResponseItemEntity;
import com.survey.api.entity.SurveyResponseOptionEntity;
import com.survey.api.response.SurveyItemResponse;
import com.survey.api.response.SurveyOptionResponse;
import com.survey.api.response.SurveyResponse;

import java.util.ArrayList;
import java.util.List;

public class VoConvertUtil {

    public static List<SurveyResponse> entityToVo(List<SurveyResponseEntity> entity) {
        List<SurveyResponse> responseList = new ArrayList<>();

        for (SurveyResponseEntity responseEntity : entity) {
            SurveyResponse response = new SurveyResponse();
            response.setId(responseEntity.getId());
            response.setDescription(responseEntity.getSnapShotDescription());
            response.setName(responseEntity.getSnapShotDescription());
            response.setRegDtm(responseEntity.getRegDtm().toString());

            List<SurveyItemResponse> surveyItemResponseList = new ArrayList<>();

            for(SurveyResponseItemEntity itemDto : responseEntity.getResponseItems()) {
                List<SurveyOptionResponse> responseOptionList = new ArrayList<>();

                SurveyItemResponse surveyItemResponse = new SurveyItemResponse();
                surveyItemResponse.setId(itemDto.getId());
                surveyItemResponse.setDescription(itemDto.getItemSnapShotDescription());
                surveyItemResponse.setItemType(itemDto.getItemSnapShotType());
                surveyItemResponse.setLongAnswer(itemDto.getLongAnswer());
                surveyItemResponse.setShortAnswer(itemDto.getShortAnswer());
                surveyItemResponse.setUseYn(itemDto.getItemSnapShotUseYn());
                surveyItemResponse.setItemName(itemDto.getItemSnapShotName());
                surveyItemResponse.setRegDtm(itemDto.getRegDtm().toString());
                surveyItemResponse.setRequired(itemDto.isItemSnapShotRequired());

                for (SurveyResponseOptionEntity optionEntity : itemDto.getResponseOption()) {
                    SurveyOptionResponse surveyOptionResponse = new SurveyOptionResponse();
                    surveyOptionResponse.setId(optionEntity.getId());
                    surveyOptionResponse.setOptionName(optionEntity.getOptionSnapShotName());
                    surveyOptionResponse.setUseYn(optionEntity.getOptionSnapShotUseYn());
                    responseOptionList.add(surveyOptionResponse);
                }

                surveyItemResponse.setSelectOptions(responseOptionList);
                surveyItemResponseList.add(surveyItemResponse);
            }
            response.setItemList(surveyItemResponseList);
            responseList.add(response);
        }
        return responseList;
    }
}

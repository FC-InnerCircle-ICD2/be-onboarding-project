package org.survey.api.domain.survey.converter;

import lombok.RequiredArgsConstructor;
import org.survey.api.common.annotation.Converter;
import org.survey.api.common.error.CommonErrorCode;
import org.survey.api.common.exception.ApiException;
import org.survey.api.domain.survey.controller.model.SurveyBaseRequest;
import org.survey.api.domain.survey.controller.model.SurveyBaseResponse;
import org.survey.api.domain.survey.controller.model.SurveyItemRequest;
import org.survey.api.domain.survey.controller.model.SurveyItemResponse;
import org.survey.db.selectlist.SelectListEntity;
import org.survey.db.surveybase.SurveyBaseEntity;
import org.survey.db.surveyitem.SurveyItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class SurveyConverter {

    public SurveyBaseEntity toEntity(SurveyBaseRequest request){
        return Optional.ofNullable(request)
                .map(it -> {
                    return SurveyBaseEntity.builder()
                            .title(request.getTitle())
                            .description(request.getDescription())
                            .build();
                })
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "SurveyBaseRequest Null"))
                ;
    }

    public SurveyItemEntity toEntity(
            SurveyItemRequest request,
            Long surveyId
            ){
        return Optional.ofNullable(request)
                .map(it -> {
                    return SurveyItemEntity.builder()
                            .surveyId(surveyId)
                            .name(request.getName())
                            .description(request.getDescription())
                            .inputType(request.getInputType())
                            .required(request.getRequired())
                            .build();
                })
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "SurveyItemRequest Null"))
                ;
    }

    public SelectListEntity toEntity(
            Long surveyId,
            Long itemId,
            String content
    ){
        return Optional.ofNullable(content)
                .map(it -> {
                    return SelectListEntity.builder()
                            .surveyId(surveyId)
                            .itemId(itemId)
                            .content(content)
                            .build();
                })
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "content Null"))
                ;
    }

    public SurveyBaseResponse toResponse(
            SurveyBaseEntity surveyBaseEntity,
            List<SurveyItemResponse> items
    ){
        return SurveyBaseResponse.builder()
                .id(surveyBaseEntity.getId())
                .title(surveyBaseEntity.getTitle())
                .description(surveyBaseEntity.getDescription())
                .items(items)
                .registeredAt(surveyBaseEntity.getRegisteredAt())
                .modifiedAt(surveyBaseEntity.getModifiedAt())
                .unregisteredAt(surveyBaseEntity.getUnregisteredAt())
                .build();
    }

    public SurveyItemResponse toResponse(
            SurveyItemEntity surveyItemEntity,
            List<String> options
    ){
        return Optional.ofNullable(surveyItemEntity)
                .map(it -> {
                    return SurveyItemResponse.builder()
                            .id(surveyItemEntity.getId())
                            .surveyId(surveyItemEntity.getSurveyId())
                            .name(surveyItemEntity.getName())
                            .description(surveyItemEntity.getDescription())
                            .inputType(surveyItemEntity.getInputType())
                            .required(surveyItemEntity.getRequired())
                            .selectOptions(options)
                            .registeredAt(surveyItemEntity.getRegisteredAt())
                            .modifiedAt(surveyItemEntity.getModifiedAt())
                            .unregisteredAt(surveyItemEntity.getUnregisteredAt())
                            .build();
                })
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "SurveyItemEntity Null"))
                ;
    }

    public List<String> toResponse(
            List<SelectListEntity> list
    ){
        List<String> result = new ArrayList<>();
        for(SelectListEntity selectListEntity : list){
            result.add(selectListEntity.getContent());
        }
        return result;
    }
}

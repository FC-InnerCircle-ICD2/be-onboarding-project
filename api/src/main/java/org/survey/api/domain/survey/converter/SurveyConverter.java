package org.survey.api.domain.survey.converter;

import lombok.RequiredArgsConstructor;
import org.survey.api.common.annotation.Converter;
import org.survey.api.common.error.CommonErrorCode;
import org.survey.api.common.exception.ApiException;
import org.survey.api.domain.survey.controller.model.*;
import org.survey.db.selectlist.SelectListEntity;
import org.survey.db.surveybase.SurveyBaseEntity;
import org.survey.db.surveyitem.SurveyItemEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                            .id(request.getId())
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
            SelectOptionRequest request,
            Long surveyId,
            Long itemId
    ){
        return Optional.ofNullable(request)
                .map(it -> {
                    return SelectListEntity.builder()
                            .id(request.getId())
                            .surveyId(surveyId)
                            .itemId(itemId)
                            .content(request.getContent())
                            .build();
                })
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "SelectOptionRequest Null"))
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
                .status(surveyBaseEntity.getStatus())
                .registeredAt(surveyBaseEntity.getRegisteredAt())
                .modifiedAt(surveyBaseEntity.getModifiedAt())
                .unregisteredAt(surveyBaseEntity.getUnregisteredAt())
                .build();
    }

    public SurveyItemResponse toResponse(
            SurveyItemEntity surveyItemEntity,
            List<SelectOptionResponse> options
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
                            .status(surveyItemEntity.getStatus())
                            .registeredAt(surveyItemEntity.getRegisteredAt())
                            .modifiedAt(surveyItemEntity.getModifiedAt())
                            .unregisteredAt(surveyItemEntity.getUnregisteredAt())
                            .build();
                })
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "SurveyItemEntity Null"))
                ;
    }

    public SelectOptionResponse toResponse(
            SelectListEntity selectListEntity
    ){
        return Optional.ofNullable(selectListEntity)
                .map(it -> {
                    return SelectOptionResponse.builder()
                            .id(selectListEntity.getItemId())
                            .surveyId(selectListEntity.getSurveyId())
                            .itemId(selectListEntity.getItemId())
                            .content(selectListEntity.getContent())
                            .status(selectListEntity.getStatus())
                            .registeredAt(selectListEntity.getRegisteredAt())
                            .modifiedAt(selectListEntity.getModifiedAt())
                            .unregisteredAt(selectListEntity.getUnregisteredAt())
                            .build();
                })
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "SelectListEntity Null"))
                ;
    }

    public SurveyListResponse toResponse(
            SurveyBaseEntity surveyBaseEntity
    ){
        return Optional.ofNullable(surveyBaseEntity)
                .map(it -> {
                    return SurveyListResponse.builder()
                            .id(surveyBaseEntity.getId())
                            .title(surveyBaseEntity.getTitle())
                            .description(surveyBaseEntity.getTitle())
                            .status(surveyBaseEntity.getStatus())
                            .registeredAt(surveyBaseEntity.getRegisteredAt())
                            .modifiedAt(surveyBaseEntity.getModifiedAt())
                            .unregisteredAt(surveyBaseEntity.getUnregisteredAt())
                            .build();
                })
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "surveyBaseEntity Null"))
                ;
    }

    public List<SurveyListResponse> toResponse(
            List<SurveyBaseEntity> surveyBaseEntityList
    ){
        return surveyBaseEntityList.stream()
                .map(it -> toResponse(it))
                .collect(Collectors.toList());
    }
}

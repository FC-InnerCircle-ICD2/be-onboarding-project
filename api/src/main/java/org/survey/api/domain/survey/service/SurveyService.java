package org.survey.api.domain.survey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.survey.api.common.error.CommonErrorCode;
import org.survey.api.common.exception.ApiException;
import org.survey.db.BaseStatus;
import org.survey.db.selectlist.SelectListEntity;
import org.survey.db.selectlist.SelectListRepository;
import org.survey.db.surveybase.SurveyBaseEntity;
import org.survey.db.surveybase.SurveyBaseRepository;
import org.survey.db.surveyitem.SurveyItemEntity;
import org.survey.db.surveyitem.SurveyItemRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SurveyService {

    private final SurveyBaseRepository surveyBaseRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final SelectListRepository selectListRepository;

    public SurveyBaseEntity baseRegister(
            SurveyBaseEntity surveyBaseEntity
    ){
        return Optional.ofNullable(surveyBaseEntity)
                .map(it ->{
                    surveyBaseEntity.setStatus(BaseStatus.REGISTERED);
                    surveyBaseEntity.setRegisteredAt(LocalDateTime.now());
                    return surveyBaseRepository.save(surveyBaseEntity);
                })
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "surveyBaseEntity Null"))
                ;
    }

    public SurveyItemEntity itemRegister(
            SurveyItemEntity surveyItemEntity
    ){
        return Optional.ofNullable(surveyItemEntity)
                .map(it ->{
                    surveyItemEntity.setStatus(BaseStatus.REGISTERED);
                    surveyItemEntity.setRegisteredAt(LocalDateTime.now());
                    return surveyItemRepository.save(surveyItemEntity);
                })
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "surveyItemEntity Null"))
                ;
    }

    public SelectListEntity selectListRegister(
            SelectListEntity selectListEntity
    ){
        return Optional.ofNullable(selectListEntity)
                .map(it ->{
                    selectListEntity.setStatus(BaseStatus.REGISTERED);
                    selectListEntity.setRegisteredAt(LocalDateTime.now());
                    return selectListRepository.save(selectListEntity);
                })
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "selectListEntity Null"))
                ;
    }

    public SurveyBaseEntity baseFind(
            Long id
    ){
        return surveyBaseRepository.findFirstByIdAndStatusOrderByIdDesc(id, BaseStatus.REGISTERED)
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "surveyBaseEntity Null"));
    }

    public List<SurveyItemEntity> itemFind(
            Long surveyId
    ){
        return surveyItemRepository.findAllBySurveyIdAndStatusOrderByIdAsc(surveyId, BaseStatus.REGISTERED);
    }

    public List<SelectListEntity> selectListFind(
            Long surveyId,
            Long itemId
    ){
        return selectListRepository.findAllBySurveyIdAndItemIdAndStatusOrderByIdAsc(
                surveyId,
                itemId,
                BaseStatus.REGISTERED);
    }

    public List<SurveyBaseEntity> baseAllFind(){
        return surveyBaseRepository.findAllByStatusOrderByIdDesc(BaseStatus.REGISTERED);
    }

    public SurveyBaseEntity deleteSurvey(Long id){
        SurveyBaseEntity surveyBaseEntity = surveyBaseRepository.findById(id)
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "surveyBaseEntity Null"));
        surveyBaseRepository.deleteById(id);
        surveyBaseEntity.setStatus(BaseStatus.UNREGISTERED);
        surveyBaseEntity.setRegisteredAt(LocalDateTime.now());
        return surveyBaseRepository.save(surveyBaseEntity);
    }
}

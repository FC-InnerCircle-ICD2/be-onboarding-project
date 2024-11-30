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

    public SurveyBaseEntity baseFindById(
            Long id
    ){
        return surveyBaseRepository.findFirstByIdAndStatusOrderByIdDesc(id, BaseStatus.REGISTERED)
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "surveyBaseEntity Null"));
    }

    public SurveyItemEntity itemFindById(
            Long id,
            Long surveyId
    ){
        return surveyItemRepository.findFirstByIdAndSurveyIdAndStatusOrderByIdDesc(
                id,
                surveyId,
                BaseStatus.REGISTERED)
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "surveyItemEntity Null"));
    }

    public SelectListEntity selectListFindById(
            Long id,
            Long surveyId,
            Long itemId
    ){
        return selectListRepository.findFirstByIdAndSurveyIdAndItemIdAndStatusOrderByIdDesc(
                id,
                surveyId,
                itemId,
                BaseStatus.REGISTERED)
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "selectListEntity Null"));
    }

    public List<SurveyBaseEntity> baseAllFind(){
        return surveyBaseRepository.findAllByStatusOrderByIdDesc(BaseStatus.REGISTERED);
    }

    public List<SurveyItemEntity> itemAllFind(
            Long surveyId
    ){
        return surveyItemRepository.findAllBySurveyIdAndStatusOrderByIdAsc(surveyId, BaseStatus.REGISTERED);
    }

    public List<SelectListEntity> selectListAllFind(
            Long surveyId,
            Long itemId
    ){
        return selectListRepository.findAllBySurveyIdAndItemIdAndStatusOrderByIdAsc(
                surveyId,
                itemId,
                BaseStatus.REGISTERED);
    }

    public SurveyBaseEntity baseDelete(Long id){
        var surveyBaseEntity = this.baseFindById(id);
        surveyBaseEntity.setStatus(BaseStatus.UNREGISTERED);
        surveyBaseEntity.setRegisteredAt(LocalDateTime.now());
        return surveyBaseRepository.save(surveyBaseEntity);
    }

    public SurveyItemEntity itemDelete(Long id, long surveyId){
        var surveyItemEntity = this.itemFindById(id, surveyId);
        surveyItemEntity.setStatus(BaseStatus.UNREGISTERED);
        surveyItemEntity.setUnregisteredAt(LocalDateTime.now());
        return surveyItemRepository.save(surveyItemEntity);
    }

    public SelectListEntity selectListDelete(Long id, long surveyId, long itemId){
        var selectListEntity = this.selectListFindById(id, surveyId, itemId);
        selectListEntity.setStatus(BaseStatus.UNREGISTERED);
        selectListEntity.setUnregisteredAt(LocalDateTime.now());
        return selectListRepository.save(selectListEntity);
    }

    public SurveyBaseEntity baseUpdate(
            Long id,
            SurveyBaseEntity surveyBaseEntity
    ){
        var newSurveyBaseEntity = this.baseFindById(id);
        if(newSurveyBaseEntity.getTitle().equals(surveyBaseEntity.getTitle())
                && newSurveyBaseEntity.getDescription().equals(surveyBaseEntity.getDescription()))
        {
            return newSurveyBaseEntity;
        }
        newSurveyBaseEntity.setTitle(surveyBaseEntity.getTitle());
        newSurveyBaseEntity.setDescription(surveyBaseEntity.getDescription());
        newSurveyBaseEntity.setModifiedAt(LocalDateTime.now());
        return surveyBaseRepository.save(newSurveyBaseEntity);
    }

    public SurveyItemEntity itemUpdate(
            SurveyItemEntity surveyItemEntity,
            Long surveyId
    ){
        var newSurveyItemEntity = surveyItemRepository.findFirstByIdAndSurveyIdAndStatusOrderByIdDesc(
                surveyItemEntity.getId(),
                surveyId,
                BaseStatus.REGISTERED).orElseGet(()->new SurveyItemEntity());
        if(newSurveyItemEntity.getId() != null
                && newSurveyItemEntity.getName().equals(surveyItemEntity.getName())
                && newSurveyItemEntity.getDescription().equals(surveyItemEntity.getDescription())
                && newSurveyItemEntity.getInputType() == surveyItemEntity.getInputType()
                && newSurveyItemEntity.getRequired().equals(surveyItemEntity.getRequired())
        ){
            return newSurveyItemEntity;
        }
        if(newSurveyItemEntity.getId() == null){
            newSurveyItemEntity.setId(surveyItemEntity.getId());
            newSurveyItemEntity.setSurveyId(surveyId);
            newSurveyItemEntity.setStatus(BaseStatus.REGISTERED);
            newSurveyItemEntity.setRegisteredAt(LocalDateTime.now());
        }
        newSurveyItemEntity.setName(surveyItemEntity.getName());
        newSurveyItemEntity.setDescription(surveyItemEntity.getDescription());
        newSurveyItemEntity.setInputType(surveyItemEntity.getInputType());
        newSurveyItemEntity.setRequired(surveyItemEntity.getRequired());
        newSurveyItemEntity.setModifiedAt(LocalDateTime.now());
        return surveyItemRepository.save(newSurveyItemEntity);
    }

    public SelectListEntity selectListUpdate(
            SelectListEntity selectListEntity,
            Long surveyId,
            Long itemId
    ){
        var newSelectListEntity = selectListRepository.findFirstByIdAndSurveyIdAndItemIdAndStatusOrderByIdDesc(
                selectListEntity.getId(),
                surveyId,
                itemId,
                BaseStatus.REGISTERED).orElseGet(()->new SelectListEntity());
        if(newSelectListEntity.getId() != null
                && newSelectListEntity.getContent().equals(selectListEntity.getContent())){
            return newSelectListEntity;
        }
        if(newSelectListEntity.getId() == null){
            newSelectListEntity.setId(selectListEntity.getId());
            newSelectListEntity.setSurveyId(surveyId);
            newSelectListEntity.setItemId(itemId);
            newSelectListEntity.setStatus(BaseStatus.REGISTERED);
            newSelectListEntity.setRegisteredAt(LocalDateTime.now());
        }
        newSelectListEntity.setContent(selectListEntity.getContent());
        newSelectListEntity.setModifiedAt(LocalDateTime.now());
        return selectListRepository.save(newSelectListEntity);
    }
}

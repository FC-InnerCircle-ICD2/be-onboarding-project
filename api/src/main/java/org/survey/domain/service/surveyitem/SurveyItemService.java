package org.survey.domain.service.surveyitem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.survey.common.error.CommonErrorCode;
import org.survey.common.exception.ApiException;
import org.survey.domain.service.BaseStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SurveyItemService {

    private final SurveyItemRepository surveyItemRepository;

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

    public SurveyItemEntity itemFindById(
            Long id,
            Long surveyId
    ){
        return surveyItemRepository.findFirstByIdAndSurveyIdAndStatusOrderByIdDesc(
                id,
                surveyId,
                BaseStatus.REGISTERED)
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "surveyItemEntity not exist"));
    }

    public SurveyItemEntity itemFindByName(
            Long surveyId,
            String name
    ){
        return surveyItemRepository.findFirstBySurveyIdAndNameAndStatusOrderByIdDesc(
                        surveyId,
                        name,
                        BaseStatus.REGISTERED)
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "surveyItemEntity not exist"));
    }

    public List<SurveyItemEntity> itemAllFind(
            Long surveyId
    ){
        return surveyItemRepository.findAllBySurveyIdAndStatusOrderByIdAsc(surveyId, BaseStatus.REGISTERED);
    }

    public SurveyItemEntity itemDelete(Long id, long surveyId){
        var surveyItemEntity = this.itemFindById(id, surveyId);
        surveyItemEntity.setStatus(BaseStatus.UNREGISTERED);
        surveyItemEntity.setUnregisteredAt(LocalDateTime.now());
        return surveyItemRepository.save(surveyItemEntity);
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
}

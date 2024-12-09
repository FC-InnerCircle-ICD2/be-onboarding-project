package org.survey.domain.service.selectlist;

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
public class SelectListService {

    private final SelectListRepository selectListRepository;

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
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "selectListEntity not exist"));
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

    public SelectListEntity selectListDelete(Long id, long surveyId, long itemId){
        var selectListEntity = this.selectListFindById(id, surveyId, itemId);
        selectListEntity.setStatus(BaseStatus.UNREGISTERED);
        selectListEntity.setUnregisteredAt(LocalDateTime.now());
        return selectListRepository.save(selectListEntity);
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

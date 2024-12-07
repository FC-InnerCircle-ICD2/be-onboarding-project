package org.survey.domain.service.surveybase;

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
public class SurveyBaseService {

    private final SurveyBaseRepository surveyBaseRepository;

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

    public SurveyBaseEntity baseFindById(
            Long id
    ){
        return surveyBaseRepository.findFirstByIdAndStatusOrderByIdDesc(id, BaseStatus.REGISTERED)
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "surveyBaseEntity not exist"));
    }

    public List<SurveyBaseEntity> baseAllFind(){
        return surveyBaseRepository.findAllByStatusOrderByIdDesc(BaseStatus.REGISTERED);
    }


    public SurveyBaseEntity baseDelete(Long id){
        var surveyBaseEntity = this.baseFindById(id);
        surveyBaseEntity.setStatus(BaseStatus.UNREGISTERED);
        surveyBaseEntity.setRegisteredAt(LocalDateTime.now());
        return surveyBaseRepository.save(surveyBaseEntity);
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
}

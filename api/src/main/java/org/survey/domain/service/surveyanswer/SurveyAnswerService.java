package org.survey.domain.service.surveyanswer;

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
public class SurveyAnswerService {

    private final SurveyReplyRepository surveyReplyRepository;

    public SurveyReplyEntity replyRegister(
            SurveyReplyEntity surveyReplyEntity
    ){
        return Optional.ofNullable(surveyReplyEntity)
                .map(it ->{
                    surveyReplyEntity.setStatus(BaseStatus.REGISTERED);
                    surveyReplyEntity.setRegisteredAt(LocalDateTime.now());
                    return surveyReplyRepository.save(surveyReplyEntity);
                })
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "surveyReplyEntity Null"))
                ;
    }

    public SurveyReplyEntity replyFindById(
            Long id,
            Long surveyId,
            Long itemId
    ){
        return surveyReplyRepository.findFirstByIdAndSurveyIdAndItemIdAndStatusOrderByIdDesc(
                        id,
                        surveyId,
                        itemId,
                        BaseStatus.REGISTERED)
                .orElseThrow(()-> new ApiException(CommonErrorCode.NULL_POINT, "SurveyReplyEntity Null"));
    }

    public List<SurveyReplyEntity> replyAllFind(
            Long surveyId
    ){
        return surveyReplyRepository.findAllBySurveyIdAndStatusOrderByIdAscItemIdAsc(
                surveyId,
                BaseStatus.REGISTERED);
    }

    public List<SurveyReplyEntity> replyFindByContent(
            Long surveyId,
            String content
    ){
        return surveyReplyRepository.findAllBySurveyIdAndContentAndStatusOrderByIdAscItemIdAsc(
                surveyId,
                content,
                BaseStatus.REGISTERED);
    }

    public List<SurveyReplyEntity> replyMultiFind(
            Long surveyId,
            Long itemId
    ){
        return surveyReplyRepository.findAllBySurveyIdAndItemIdAndStatusOrderByIdAsc(
                surveyId,
                itemId,
                BaseStatus.REGISTERED);
    }

    public SurveyReplyEntity replyDelete(Long id, long surveyId, long itemId){
        var surveyReplyEntity = this.replyFindById(id, surveyId, itemId);
        surveyReplyEntity.setStatus(BaseStatus.UNREGISTERED);
        surveyReplyEntity.setUnregisteredAt(LocalDateTime.now());
        return surveyReplyRepository.save(surveyReplyEntity);
    }
}

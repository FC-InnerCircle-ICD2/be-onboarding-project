package com.innercicle.adapter.out.persistence.v1;

import com.innercicle.advice.exceptions.NotExistsReplySurveyException;
import com.innercicle.annotations.PersistenceAdapter;
import com.innercicle.application.port.out.v1.ReplySurveyOutPortV1;
import com.innercicle.application.port.out.v1.SearchReplySurveyOutPortV1;
import com.innercicle.domain.ReplySurvey;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class ReplySurveyOutPortAdapter implements ReplySurveyOutPortV1, SearchReplySurveyOutPortV1 {

    private final ReplySurveyRepository replySurveyRepository;

    @Override
    @Transactional
    public ReplySurvey replySurvey(ReplySurvey replySurvey) {
        ReplySurveyEntity replySurveyEntity = ReplySurveyEntity.from(replySurvey);
        replySurveyEntity = replySurveyRepository.save(replySurveyEntity);
        return replySurveyEntity.mapToDomain(replySurvey.surveyId());
    }

    @Override
    public ReplySurvey searchReplySurvey(Long replySurveyId) {
        ReplySurveyEntity replySurveyEntity =
            replySurveyRepository.findByIdAndSearchKeyword(replySurveyId).orElseThrow(NotExistsReplySurveyException::new);
        return replySurveyEntity.mapToDomain(replySurveyId);
    }

    @Override
    public List<ReplySurvey> searchRepliesSurvey(Long replySurveyId, String searchKeyword) {
        List<ReplySurveyEntity> repliesSurveyEntities = replySurveyRepository.findAllByIdOrSearchKeyword(replySurveyId, searchKeyword);
        return List.of();
    }

}

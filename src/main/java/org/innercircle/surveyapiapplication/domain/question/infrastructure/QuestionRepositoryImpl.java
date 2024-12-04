package org.innercircle.surveyapiapplication.domain.question.infrastructure;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.question.entity.QuestionEntity;
import org.innercircle.surveyapiapplication.domain.question.entity.id.QuestionId;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

    private final QuestionJpaRepository questionJpaRepository;

    @Override
    public Question save(Question question) {
        return questionJpaRepository.save(QuestionEntity.from(question)).toDomain();
    }

    @Override
    public List<Question> findBySurveyId(Long surveyId) {
        return questionJpaRepository.findBySurveyId(surveyId).stream().map(QuestionEntity::toDomain).toList();
    }

    @Override
    public Question findByIdAndVersion(Long id, int version) {
        QuestionId questionId = new QuestionId(id, version);
        return questionJpaRepository.findById(questionId)
            .orElseThrow(() -> new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION))
            .toDomain();
    }

}

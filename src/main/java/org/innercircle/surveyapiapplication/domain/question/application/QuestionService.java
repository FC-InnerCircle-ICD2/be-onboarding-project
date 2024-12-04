package org.innercircle.surveyapiapplication.domain.question.application;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.question.infrastructure.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question findByIdAndVersion(Long id, int version) {
        return questionRepository.findByIdAndVersion(id, version);
    }

}

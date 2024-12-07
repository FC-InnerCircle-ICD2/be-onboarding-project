package ic2.onboarding.survey.repository;

import ic2.onboarding.survey.entity.AnswerHistory;
import ic2.onboarding.survey.entity.Survey;
import ic2.onboarding.survey.entity.SurveySubmission;
import ic2.onboarding.survey.service.out.SurveyStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SurveyJpaDao implements SurveyStorage {

    private final SurveyJpaRepository surveyJpaRepository;

    private final SurveySubmissionJpaRepository surveySubmissionJpaRepository;

    private final AnswerHistoryJpaRepository answerHistoryJpaRepository;


    @Override
    public Survey save(Survey survey) {
        return surveyJpaRepository.save(survey);
    }

    public Optional<Survey> findByUuid(String uuid) {
        return surveyJpaRepository.findFirstByUuid(uuid);
    }

    @Override
    public SurveySubmission save(SurveySubmission submission) {
        return surveySubmissionJpaRepository.save(submission);
    }

    @Override
    public List<AnswerHistory> saveAll(List<AnswerHistory> histories) {
        return answerHistoryJpaRepository.saveAll(histories);
    }

    @Override
    public List<SurveySubmission> findAllBySurveyUuid(String uuid) {
        return surveySubmissionJpaRepository.findAllBySurveyUuid(uuid);
    }

    @Override
    public List<AnswerHistory> findAllBySurveyUuidAndQuestionName(String uuid, String questionName) {
        return answerHistoryJpaRepository.findAllBySurveyUuidAndQuestionName(uuid, questionName);
    }

}

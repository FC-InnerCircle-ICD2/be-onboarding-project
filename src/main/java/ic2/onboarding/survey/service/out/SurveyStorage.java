package ic2.onboarding.survey.service.out;

import ic2.onboarding.survey.entity.AnswerHistory;
import ic2.onboarding.survey.entity.Survey;
import ic2.onboarding.survey.entity.SurveySubmission;

import java.util.List;
import java.util.Optional;

public interface SurveyStorage {

    Survey save(Survey survey);

    Optional<Survey> findByUuid(String uuid);

    SurveySubmission save(SurveySubmission submission);

    List<AnswerHistory> saveAll(List<AnswerHistory> histories);

    List<SurveySubmission> findAllBySurveyUuid(String uuid);

    List<AnswerHistory> findAllBySurveyUuidAndQuestionName(String uuid, String questionName);

}

package com.survey.www.surveys.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.survey.www.surveys.dto.command.QSurveyDetailQuestionCommand;
import com.survey.www.surveys.dto.command.SurveyDetailQuestionCommand;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.survey.www.surveys.domain.QSurveyQuestionOptions.surveyQuestionOptions;
import static com.survey.www.surveys.domain.QSurveyQuestions.surveyQuestions;

@RequiredArgsConstructor
public class SurveyQuestionsDslRepositoryImpl implements SurveyQuestionsDslRepository {
    private final JPAQueryFactory factory;

    public List<SurveyDetailQuestionCommand> searchBySurveyId(Long surveyId) {
        return factory.select(new QSurveyDetailQuestionCommand(surveyQuestions.id
                                                             , surveyQuestions.surveyQuestionType
                                                             , surveyQuestions.questionNm
                                                             , surveyQuestions.description
                                                             , surveyQuestions.isRequired
                                                             , surveyQuestions.isDeleted
                                                             , surveyQuestionOptions.id
                                                             , surveyQuestionOptions.content
                                                             , surveyQuestionOptions.isDeleted
                                                             , surveyQuestionOptions.surveyQuestions.id))
                      .from(surveyQuestions)
                      .leftJoin(surveyQuestionOptions).on(surveyQuestionOptions.surveyQuestions.id.eq(surveyQuestions.id)
                              , surveyQuestionOptions.isDeleted.isFalse())
                      .where(surveyQuestions.survey.id.eq(surveyId)
                           , surveyQuestions.isDeleted.isFalse())
                      .orderBy(surveyQuestions.id.asc())
                      .fetch();
    }
}

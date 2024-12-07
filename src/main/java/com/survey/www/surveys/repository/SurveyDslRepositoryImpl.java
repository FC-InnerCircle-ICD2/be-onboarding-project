package com.survey.www.surveys.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.survey.www.surveys.dto.command.QSurveyDetailAnswerCommand;
import com.survey.www.surveys.dto.command.SurveyDetailAnswerCommand;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.survey.www.surveys.domain.QSurvey.survey;
import static com.survey.www.surveys.domain.QSurveyAnswerQuestions.surveyAnswerQuestions;
import static com.survey.www.surveys.domain.QSurveyQuestionOptions.surveyQuestionOptions;
import static com.survey.www.surveys.domain.QSurveyQuestions.surveyQuestions;

@RequiredArgsConstructor
public class SurveyDslRepositoryImpl implements SurveyDslRepository {
    private final JPAQueryFactory factory;

    @Override
    public List<SurveyDetailAnswerCommand> searchBySurveyId(Long surveyId, String questionNm, String optionContent, String answerContent) {
        return factory.select(new QSurveyDetailAnswerCommand(
                              surveyQuestions.id
                            , surveyQuestions.surveyQuestionType
                            , surveyQuestions.questionNm
                            , surveyQuestionOptions.id.as("surveyQuestionOptionId")
                            , surveyQuestionOptions.content.as("optionContent")
                            , JPAExpressions.select(Expressions.stringTemplate("JSON_ARRAYAGG({0})", surveyAnswerQuestions.content))
                                            .from(surveyAnswerQuestions)
                                            .where(surveyAnswerQuestions.surveyQuestions.id.eq(surveyQuestions.id)
                                                 , surveyAnswerQuestions.content.isNotEmpty())
                            , JPAExpressions.select(surveyAnswerQuestions.count())
                                            .from(surveyAnswerQuestions)
                                            .where(surveyAnswerQuestions.surveyQuestionOptions.id.eq(surveyQuestionOptions.id))
                            , JPAExpressions.select(surveyQuestionOptions.count())
                                            .from(surveyQuestionOptions)
                                            .where(surveyQuestionOptions.surveyQuestions.id.eq(surveyQuestions.id))))
                      .from(survey)
                      .leftJoin(surveyQuestions).on(surveyQuestions.survey.id.eq(survey.id))
                      .leftJoin(surveyQuestionOptions).on(surveyQuestionOptions.surveyQuestions.id.eq(surveyQuestions.id))
                      .where(survey.id.eq(surveyId)
                           , isQuestionNmContains(questionNm)
                           , isOptionContentContains(optionContent)
                           , isAnswerContentContains(answerContent)
                      )
                      .orderBy(surveyQuestions.id.asc(), surveyQuestionOptions.id.asc())
                      .fetch();
    }

    private BooleanExpression isQuestionNmContains(String questionNm) {
        if (questionNm == null || questionNm.isEmpty()) {
            return null;
        }

        return surveyQuestions.questionNm.contains(questionNm);
    }

    private BooleanExpression isOptionContentContains(String optionContent) {
        if (optionContent == null || optionContent.isEmpty()) {
            return null;
        }

        return surveyQuestionOptions.content.contains(optionContent);
    }

    private BooleanExpression isAnswerContentContains(String answerContent) {
        if (answerContent == null || answerContent.isEmpty()) {
            return null;
        }

        StringTemplate aggregatedContent = Expressions.stringTemplate("CAST(GROUP_CONCAT({0}) AS STRING)", surveyAnswerQuestions.content);
        return Expressions.stringTemplate("({0})", JPAExpressions.select(aggregatedContent)
                                                                          .from(surveyAnswerQuestions)
                                                                          .where(surveyAnswerQuestions.surveyQuestions.id.eq(surveyQuestions.id)
                                                                               , surveyAnswerQuestions.content.isNotEmpty())).like("%" + answerContent + "%");
    }
}

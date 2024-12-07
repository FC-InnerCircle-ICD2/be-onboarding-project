package ic2.onboarding.survey.service;

import ic2.onboarding.survey.dto.AnswerInfo;
import ic2.onboarding.survey.dto.SurveyAnswer;
import ic2.onboarding.survey.dto.SurveyInfo;
import ic2.onboarding.survey.entity.AnswerHistory;
import ic2.onboarding.survey.entity.Survey;
import ic2.onboarding.survey.entity.SurveySubmission;
import ic2.onboarding.survey.global.BizException;
import ic2.onboarding.survey.global.ErrorCode;
import ic2.onboarding.survey.global.InputType;
import ic2.onboarding.survey.service.out.SurveyStorage;
import ic2.onboarding.survey.validator.SubmissionValidator;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveySubmissionService {

    private final SurveyStorage surveyStorage;


    @Transactional
    public SurveyAnswer submitSurvey(String uuid, SurveyAnswer surveyAnswer) {

        final Survey survey = surveyStorage.findByUuid(uuid)
                .orElseThrow(() -> new BizException(ErrorCode.NOT_FOUND));

        SurveyInfo surveyData = survey.getSurveyInfo();
        List<SurveyInfo.Question> questions = surveyData.getQuestions();

        // <질문이름, 질문객체>
        var surveyQuestionByName = questions.stream()
                .collect(Collectors.toMap(SurveyInfo.Question::getName, question -> question));
        // <답변 질문이름, 답변객체>
        List<AnswerInfo> answers = surveyAnswer.getAnswers();
        var answerByQuestionName = answers.stream()
                .collect(Collectors.toMap(AnswerInfo::getQuestionName, answer -> answer));

        // 검증 및 저장
        SurveySubmission submission = saveSubmissionWithValidation(surveyAnswer, surveyQuestionByName, answerByQuestionName, survey);

        // 검색용 테이블에 개별 답변 추가 저장
        saveAnswerHistories(surveyQuestionByName, answers, submission);

        surveyAnswer.setSubmissionId(submission.getId());
        return surveyAnswer;
    }


    public List<AnswerInfo> retrieveSurveySubmissions(String uuid, String questionName, String answer) {

        // 검색 조건 온전하지 않으면 전체조회
        if (StringUtils.isBlank(questionName) || StringUtils.isBlank(answer)) {
            return surveyStorage.findAllBySurveyUuid(uuid)
                    .stream()
                    .flatMap(surveySubmission -> surveySubmission.getAnswerInfos().stream())
                    .toList();
        }

        // 검색 조건 존재시 설문 식별자와 질문명으로 답변 히스토리 검색
        return surveyStorage.findAllBySurveyUuidAndQuestionName(uuid, questionName)
                .stream()
                // 히스토리중 답변 내용과 일치하는것을 찾는다.
                .filter(answerHistory -> Objects.equals(answer, answerHistory.getTextAnswer()))
                // 응답했던 내용
                .map(AnswerHistory::getAnswerInfo)
                .toList();
    }

    /* PRIVATE */

    private void saveAnswerHistories(Map<String, SurveyInfo.Question> surveyQuestionByName, List<AnswerInfo> answers, SurveySubmission submission) {

        List<AnswerHistory> answerHistories = answers.stream().flatMap(answer -> {
                    SurveyInfo.Question question = surveyQuestionByName.get(answer.getQuestionName());

                    // 다중선택 답변이었다면 분리
                    if (question.getInputType() == InputType.MULTIPLE_CHOICE) {
                        return answer.getMultipleTextAnswer()
                                .stream()
                                .map(textAnswer -> createAnswerHistory(answer, textAnswer, submission, question))
                                .toList()
                                .stream();
                    }

                    return Stream.of(createAnswerHistory(answer, answer.getSingleTextAnswer(), submission, question));
                })
                .toList();

        // 답변 개별항목 저장
        surveyStorage.saveAll(answerHistories);
    }


    private SurveySubmission saveSubmissionWithValidation(SurveyAnswer surveyAnswer,
                                                          Map<String, SurveyInfo.Question> surveyQuestionByName,
                                                          Map<String, AnswerInfo> answerByQuestionName,
                                                          Survey survey) {
        // 입력 답변과 설문조사 양식 검증
        SubmissionValidator.validate(surveyQuestionByName, answerByQuestionName);

        // 제출내용 저장
        return surveyStorage.save(SurveySubmission.builder()
                .surveyUuid(survey.getUuid())
                .surveyInfo(survey.getSurveyInfo())
                .answerInfos(surveyAnswer.getAnswers())
                .build()
        );
    }


    private static AnswerHistory createAnswerHistory(AnswerInfo answerInfo,
                                                     String textAnswer,
                                                     SurveySubmission submission,
                                                     SurveyInfo.Question question) {


        return AnswerHistory.builder()
                .submissionId(submission.getId())
                .surveyUuid(submission.getSurveyUuid())
                .questionName(question.getName())
                .textAnswer(textAnswer)
                .questionInfo(question)
                .answerInfo(answerInfo)
                .build();
    }


}

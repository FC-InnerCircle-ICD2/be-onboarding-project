package ic2.onboarding.survey.service;

import ic2.onboarding.survey.dto.AnswerInfo;
import ic2.onboarding.survey.validator.SubmissionValidator;
import ic2.onboarding.survey.dto.SurveyAnswer;
import ic2.onboarding.survey.dto.SurveyInfo;
import ic2.onboarding.survey.entity.AnswerHistory;
import ic2.onboarding.survey.entity.Survey;
import ic2.onboarding.survey.entity.SurveySubmission;
import ic2.onboarding.survey.global.BizException;
import ic2.onboarding.survey.global.ErrorCode;
import ic2.onboarding.survey.global.InputType;
import ic2.onboarding.survey.service.out.SurveyStorage;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        // 입력 답변과 설문조사 양식 검증
        SubmissionValidator.validate(surveyQuestionByName, answerByQuestionName);

        // 제출내용 저장
        SurveySubmission submission = surveyStorage.save(SurveySubmission.builder()
                .surveyUuid(survey.getUuid())
                .surveyData(surveyData)
                .answerData(surveyAnswer.getAnswers())
                .build()
        );

        List<AnswerHistory> answerHistories = new ArrayList<>();
        // 검색용 테이블에 개별 답변 저장
        answers.forEach(answer -> {
            SurveyInfo.Question question = surveyQuestionByName.get(answer.getQuestionName());

            // 다중선택 답변이었다면 분리
            if (question.getInputType() == InputType.MULTIPLE_CHOICE) {
                answerHistories.addAll(
                        answer.getMultipleTextAnswer()
                                .stream()
                                .map(textAnswer -> createAnswerHistory(answer, textAnswer, submission, question))
                                .toList()
                );
            } else {
                answerHistories.add(createAnswerHistory(answer, answer.getSingleTextAnswer(), submission, question));
            }
        });

        // 답변 개별항목 저장
        surveyStorage.saveAll(answerHistories);
        surveyAnswer.setSubmissionId(submission.getId());
        return surveyAnswer;
    }


    public List<AnswerInfo> retrieveSurveySubmissions(String uuid, String questionName, String answer) {

        // 검색 조건 없다면 전체조회
        boolean all = StringUtils.isBlank(questionName) || StringUtils.isBlank(answer);

        if (all) {
            return surveyStorage.findAllBySurveyUuid(uuid)
                    .stream()
                    .flatMap(surveySubmission -> surveySubmission.getAnswerData().stream())
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


    private static AnswerHistory createAnswerHistory(AnswerInfo answerInfo,
                                                     String textAnswer,
                                                     SurveySubmission submission,
                                                     SurveyInfo.Question question) {


        return AnswerHistory.builder()
                .submissionId(submission.getId())
                .surveyUuid(submission.getSurveyUuid())
                .questionName(question.getName())
                .textAnswer(textAnswer)
                .questionData(question)
                .answerInfo(answerInfo)
                .build();
    }


}

package com.metsakurr.beonboardingproject.domain.submission.service;

import com.metsakurr.beonboardingproject.common.dto.ApiResponse;
import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import com.metsakurr.beonboardingproject.common.exception.ServiceException;
import com.metsakurr.beonboardingproject.domain.submission.dto.SubmissionCreationRequest;
import com.metsakurr.beonboardingproject.domain.submission.dto.SubmissionCreationResponse;
import com.metsakurr.beonboardingproject.domain.submission.dto.SubmissionDetailResponse;
import com.metsakurr.beonboardingproject.domain.submission.entity.Answer;
import com.metsakurr.beonboardingproject.domain.submission.entity.Submission;
import com.metsakurr.beonboardingproject.domain.submission.repository.SubmissionRepository;
import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import com.metsakurr.beonboardingproject.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SurveyRepository surveyRepository;
    private final SubmissionRepository submissionRepository;

    @Transactional
    public ApiResponse<SubmissionCreationResponse> create(SubmissionCreationRequest request) {
        long surveyIdx = request.getIdx();
        Survey survey = surveyRepository.findById(surveyIdx)
                .orElseThrow(() -> new ServiceException(ResponseCode.NOT_FOUND_SURVEY));

        List<Question> questions = survey.getQuestions();

        Submission submission = Submission.builder().survey(survey).build();


        // TODO: 모든 질문에 답변이 되었는지, 필수 질문에 답변 되었는지 안되었는지 체크 필요
        questions.forEach(question -> {
            SubmissionCreationRequest.AnswerRequest answerRequest = request.getAnswers()
                    .stream().filter(answer -> answer.getIdx() == question.getIdx())
                    .findFirst()
                    .orElseThrow(() -> new ServiceException(ResponseCode.NOT_FOUND_ANSWER));

            String answerString = answerRequest.getAnswer();
            question.validAnswer(answerString);

            Answer answerEntity = Answer.builder()
                    .question(question)
                    .answerText(answerString)
                    .build();
            submission.addAnswer(answerEntity);
        });

        submissionRepository.save(submission);
        SubmissionCreationResponse response = SubmissionCreationResponse.fromEntity(submission);
        return new ApiResponse<>(ResponseCode.SUCCESS, response);
    }

    public ApiResponse<SubmissionDetailResponse> detail(long idx) {
        Submission response = submissionRepository.findById(idx)
                .orElseThrow(() -> new ServiceException(ResponseCode.NOT_FOUND_SUBMISSION));
        SubmissionDetailResponse detailAnswerResponse = new SubmissionDetailResponse(response);
        return new ApiResponse<>(ResponseCode.SUCCESS, detailAnswerResponse);
    }
}

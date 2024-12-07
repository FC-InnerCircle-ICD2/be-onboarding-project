package com.survey.www.surveys.service;

import com.survey.www.accounts.domain.Account;
import com.survey.www.accounts.service.AccountService;
import com.survey.www.surveys.code.SurveyQuestionType;
import com.survey.www.surveys.domain.*;
import com.survey.www.surveys.dto.command.SurveyDetailAnswerCommand;
import com.survey.www.surveys.dto.command.SurveyDetailQuestionCommand;
import com.survey.www.surveys.dto.request.SurveyAnswerCreateRequest;
import com.survey.www.surveys.dto.request.SurveyAnswerDetailRequest;
import com.survey.www.surveys.dto.request.SurveyCreateRequest;
import com.survey.www.surveys.dto.request.SurveyUpdateRequest;
import com.survey.www.surveys.dto.response.*;
import com.survey.www.surveys.exception.SurveyException;
import com.survey.www.surveys.exception.SurveyExceptionResult;
import com.survey.www.surveys.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SurveyService {
    private final AccountService accountService;
    private final SurveyRepository surveyRepository;
    private final SurveyQuestionsRepository surveyQuestionsRepository;
    private final SurveyQuestionsOptionsRepository surveyQuestionsOptionsRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;
    private final SurveyAnswerQuestionsRepository surveyAnswerQuestionsRepository;

    @Transactional
    public SurveyCreateResponse createQuestion(SurveyCreateRequest surveyCreateRequest) {
        validateSurveyQuestions(surveyCreateRequest.getSurveyQuestions().size());
        Survey savedSurvey = savedSurvey(surveyCreateRequest);
        savedSurveyQuestions(surveyCreateRequest.getSurveyQuestions(), savedSurvey);
        return new SurveyCreateResponse(savedSurvey.getId());
    }

    @Transactional(readOnly = true)
    public SurveyQuestionDetailResponse detailQuestion(Long surveyId) {
        Survey findSurvey = findSurveyById(surveyId);
        List<SurveyDetailQuestionCommand> surveyDetailCommands = surveyQuestionsRepository.searchBySurveyId(surveyId);
        List<SurveyQuestionDetailResponse.SurveyQuestionCommand> surveyQuestions = createSurveyQuestionCommands(surveyDetailCommands);
        return createSurveyDetailResponse(findSurvey, surveyQuestions);
    }

    @Transactional
    public SurveyUpdateResponse update(Long surveyId, SurveyUpdateRequest surveyUpdateRequest) {
        Survey findSurvey = findSurveyById(surveyId);
        updateSurvey(findSurvey, surveyUpdateRequest);
        updateSurveyQuestions(surveyUpdateRequest.getSurveyQuestions());
        return new SurveyUpdateResponse(surveyId);
    }

    @Transactional
    public SurveyAnswerCreateResponse createAnswer(Long surveyId, SurveyAnswerCreateRequest surveyAnswerCreateRequest) {
        Survey findSurvey = findSurveyById(surveyId);
        SurveyAnswers savedSurveyAnswer = savedSurveyAnswer(findSurvey, surveyAnswerCreateRequest);
        savedSurveyAnswerQuestions(surveyAnswerCreateRequest.getSurveyAnswerQuestions(), savedSurveyAnswer);
        return new SurveyAnswerCreateResponse(savedSurveyAnswer.getId());
    }

    @Transactional(readOnly = true)
    public List<SurveyAnswerDetailResponse> detailAnswer(Long surveyId, SurveyAnswerDetailRequest surveyAnswerDetailRequest) {
        List<SurveyAnswerDetailResponse> result = new ArrayList<>();
        List<SurveyDetailAnswerCommand> surveyDetailAnswerCommands = surveyRepository.searchBySurveyId(surveyId, surveyAnswerDetailRequest.getQuestionNm(), surveyAnswerDetailRequest.getOptionContent(), surveyAnswerDetailRequest.getAnswerContent());

        Long currentQuestionId = null;
        String currentQuestionNm = null;
        SurveyQuestionType currentSurveyQuestionType = null;
        List<SurveyAnswerDetailResponse.AnswerCommand> answerCommandList = new ArrayList<>();
        int totalCnt = 0;

        for (SurveyDetailAnswerCommand surveyDetailAnswerCommand : surveyDetailAnswerCommands) {
            if (!surveyDetailAnswerCommand.surveyQuestionId().equals(currentQuestionId)) {
                processPreviousQuestion(result, currentSurveyQuestionType, currentQuestionNm, answerCommandList, totalCnt);

                currentQuestionId = surveyDetailAnswerCommand.surveyQuestionId();
                currentQuestionNm = surveyDetailAnswerCommand.questionNm();
                currentSurveyQuestionType = surveyDetailAnswerCommand.surveyQuestionType();
                answerCommandList = new ArrayList<>();
                totalCnt = 0;
            }

            processAnswer(surveyDetailAnswerCommand, answerCommandList);
            totalCnt += surveyDetailAnswerCommand.selectedCnt().intValue();
        }

        if (!answerCommandList.isEmpty() && currentQuestionNm != null) {
            processPreviousQuestion(result, currentSurveyQuestionType, currentQuestionNm, answerCommandList, totalCnt);
        }

        return result;
    }

    private void validateAccount(Long accountId) {
        Account loginAccount = accountService.getLoginAccount();
        if (!loginAccount.getId().equals(accountId)) {
            throw new SurveyException(SurveyExceptionResult.UNAUTHORIZED_SURVEY_MODIFICATION);
        }
    }

    private void updateSurvey(Survey findSurvey, SurveyUpdateRequest surveyUpdateRequest) {
        findSurvey.update(surveyUpdateRequest.getSurveyNm(), surveyUpdateRequest.getDescription());
    }

    private void updateSurveyQuestions(List<SurveyUpdateRequest.SurveyQuestionCommand> surveyQuestionCommands) {
        for (SurveyUpdateRequest.SurveyQuestionCommand surveyQuestionCommand : surveyQuestionCommands) {
            SurveyQuestions findSurveyQuestion = findSurveyQuestionById(surveyQuestionCommand.getId());
            findSurveyQuestion.update(
                    surveyQuestionCommand.getQuestionNm(),
                    surveyQuestionCommand.getDescription(),
                    surveyQuestionCommand.getSurveyQuestionType(),
                    surveyQuestionCommand.getIsRequired(),
                    surveyQuestionCommand.getIsDeleted()
            );

            for (SurveyUpdateRequest.SurveyQuestionOptionCommand surveyQuestionOptionCommand : surveyQuestionCommand.getSurveyQuestionOptions()) {
                SurveyQuestionOptions findSurveyQuestionOption = findSurveyQuestionOptionById(surveyQuestionOptionCommand.getId());
                findSurveyQuestionOption.update(surveyQuestionOptionCommand.getContent(), surveyQuestionOptionCommand.getIsDeleted());
            }
        }
    }

    private SurveyAnswerQuestions createSurveyAnswerQuestion(SurveyAnswerCreateRequest.SurveyAnswerQuestionCommand surveyAnswerQuestionCommand, SurveyAnswers savedSurveyAnswer, SurveyQuestions findSurveyQuestion, SurveyQuestionOptions findSurveyQuestionOption) {
        return surveyAnswerQuestionCommand.toEntity(savedSurveyAnswer, findSurveyQuestion, findSurveyQuestionOption);
    }

    private void validateSurveyQuestions(int surveyQuestionsCount) {
        if (surveyQuestionsCount < 1 || surveyQuestionsCount > 10) {
            throw new SurveyException(SurveyExceptionResult.INVALID_COUNT_SURVEY_QUESTIONS);
        }
    }

    private Survey savedSurvey(SurveyCreateRequest formCreateRequest) {
        return surveyRepository.save(createSurvey(formCreateRequest));
    }

    private Survey createSurvey(SurveyCreateRequest formCreateRequest) {
        return formCreateRequest.toEntity(accountService.getLoginAccount());
    }

    private void savedSurveyQuestions(List<SurveyCreateRequest.SurveyQuestionCommand> surveyQuestionRequests, Survey survey) {
        for (SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest : surveyQuestionRequests) {
            SurveyQuestions savedSurveyQuestions = savedSurveyQuestions(survey, surveyQuestionRequest);
            savedSurveyQuestionsOptions(surveyQuestionRequest, savedSurveyQuestions);
        }
    }

    private void savedSurveyQuestionsOptions(SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest, SurveyQuestions savedSurveyQuestions) {
        surveyQuestionsOptionsRepository.saveAll(createSurveyQuestionOptions(surveyQuestionRequest.getSurveyQuestionOptions(), savedSurveyQuestions));
    }

    private SurveyQuestions savedSurveyQuestions(Survey survey, SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest) {
        return surveyQuestionsRepository.save(createSurveyQuestions(survey, surveyQuestionRequest));
    }

    private SurveyQuestions createSurveyQuestions(Survey survey, SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest) {
        return surveyQuestionRequest.toEntity(survey);
    }

    private List<SurveyQuestionOptions> createSurveyQuestionOptions(List<SurveyCreateRequest.SurveyQuestionOptionCommand> surveyQuestionOptions, SurveyQuestions savedSurveyQuestions) {
        return surveyQuestionOptions.stream()
                                    .map(optionRequest -> optionRequest.toEntity(savedSurveyQuestions))
                                    .collect(Collectors.toList());
    }

    private static List<SurveyQuestionDetailResponse.SurveyQuestionCommand> createSurveyQuestionCommands(List<SurveyDetailQuestionCommand> surveyDetailCommands) {
        List<SurveyQuestionDetailResponse.SurveyQuestionCommand> resultList = new ArrayList<>();

        Map<Long, List<SurveyDetailQuestionCommand>> groupedByQuestionId = surveyDetailCommands.stream().collect(Collectors.groupingBy(SurveyDetailQuestionCommand::surveyQuestionId));
        for (Map.Entry<Long, List<SurveyDetailQuestionCommand>> entry : groupedByQuestionId.entrySet()) {
            List<SurveyDetailQuestionCommand> commands = entry.getValue();
            if (commands.isEmpty()) {
                continue;
            }

            Long surveyQuestionId = entry.getKey();
            List<SurveyQuestionDetailResponse.SurveyQuestionOptionCommand> surveyQuestionOptions = new ArrayList<>();
            for (SurveyDetailQuestionCommand command : commands) {
                if (command.surveyQuestionOptionParentId() != null && command.surveyQuestionOptionParentId().equals(surveyQuestionId)) {
                    surveyQuestionOptions.add(
                            SurveyQuestionDetailResponse.SurveyQuestionOptionCommand.builder()
                                                                                    .id(command.surveyQuestionOptionId())
                                                                                    .content(command.surveyQuestionOptionContent())
                                                                                    .isDeleted(command.isDeletedSurveyQuestionOption())
                                                                                    .build()
                    );
                }
            }

            SurveyDetailQuestionCommand firstCommand = commands.get(0);
            resultList.add(
                    SurveyQuestionDetailResponse.SurveyQuestionCommand.builder()
                                                                      .id(surveyQuestionId)
                                                                      .surveyQuestionType(firstCommand.surveyQuestionType())
                                                                      .questionNm(firstCommand.surveyQuestionNm())
                                                                      .description(firstCommand.surveyQuestionDescription())
                                                                      .isRequired(firstCommand.isRequired())
                                                                      .isDeleted(firstCommand.isDeletedSurveyQuestion())
                                                                      .surveyQuestionOptions(surveyQuestionOptions)
                                                                      .build()
            );
        }

        return resultList;
    }

    private static SurveyQuestionDetailResponse createSurveyDetailResponse(Survey survey, List<SurveyQuestionDetailResponse.SurveyQuestionCommand> surveyQuestions) {
        return SurveyQuestionDetailResponse.builder()
                                           .surveyNm(survey.getSurveyNm())
                                           .description(survey.getDescription())
                                           .isDeleted(survey.getIsDeleted())
                                           .surveyQuestions(surveyQuestions)
                                           .build();
    }

    private Survey findSurveyById(Long id) {
        Survey survey = surveyRepository.findById(id).orElseThrow(() -> new SurveyException(SurveyExceptionResult.NOT_FOUND_SURVEY));
        if (survey.getIsDeleted()) {
            throw new SurveyException(SurveyExceptionResult.NOT_EXIST_SURVEY);
        }
        return survey;
    }

    private SurveyQuestions findSurveyQuestionById(Long id) {
        SurveyQuestions surveyQuestions = surveyQuestionsRepository.findById(id).orElseThrow(() -> new SurveyException(SurveyExceptionResult.NOT_FOUND_SURVEY_QUESTION));
        if (surveyQuestions.getIsDeleted()) {
            throw new SurveyException(SurveyExceptionResult.NOT_EXIST_SURVEY_QUESTION);
        }
        return surveyQuestions;
    }

    private SurveyQuestionOptions findSurveyQuestionOptionById(Long id) {
        SurveyQuestionOptions surveyQuestionOptions = surveyQuestionsOptionsRepository.findById(id).orElseThrow(() -> new SurveyException(SurveyExceptionResult.NOT_FOUND_SURVEY_QUESTION));
        if (surveyQuestionOptions.getIsDeleted()) {
            throw new SurveyException(SurveyExceptionResult.NOT_EXIST_SURVEY_QUESTION_OPTION);
        }
        return surveyQuestionOptions;
    }

    private SurveyAnswers savedSurveyAnswer(Survey findSurvey, SurveyAnswerCreateRequest surveyAnswerCreateRequest) {
        return surveyAnswerRepository.save(createSurveyAnswer(findSurvey, surveyAnswerCreateRequest));
    }

    private static SurveyAnswers createSurveyAnswer(Survey findSurvey, SurveyAnswerCreateRequest surveyAnswerCreateRequest) {
        return surveyAnswerCreateRequest.toEntity(findSurvey);
    }

    private void savedSurveyAnswerQuestions(List<SurveyAnswerCreateRequest.SurveyAnswerQuestionCommand> surveyAnswerQuestions, SurveyAnswers savedSurveyAnswer) {
        for (SurveyAnswerCreateRequest.SurveyAnswerQuestionCommand surveyAnswerQuestionCommand : surveyAnswerQuestions) {
            SurveyQuestions findSurveyQuestion = findSurveyQuestionById(surveyAnswerQuestionCommand.getSurveyQuestionId());

            List<SurveyAnswerQuestions> surveyAnswerQuestionsList = new ArrayList<>();
            if (SurveyQuestionType.SHORT_ANSWER == surveyAnswerQuestionCommand.getSurveyQuestionType() || SurveyQuestionType.LONG_ANSWER == surveyAnswerQuestionCommand.getSurveyQuestionType()) {
                surveyAnswerQuestionsList.add(createSurveyAnswerQuestion(surveyAnswerQuestionCommand, savedSurveyAnswer, findSurveyQuestion, null));
            }
            else {
                for (Long surveyQuestionOptionId : surveyAnswerQuestionCommand.getSurveyQuestionOptionIds()) {
                    surveyAnswerQuestionsList.add(createSurveyAnswerQuestion(surveyAnswerQuestionCommand, savedSurveyAnswer, findSurveyQuestion, findSurveyQuestionOptionById(surveyQuestionOptionId)));
                }

                if (surveyAnswerQuestionsList.isEmpty()) {
                    surveyAnswerQuestionsList.add(createSurveyAnswerQuestion(surveyAnswerQuestionCommand, savedSurveyAnswer, findSurveyQuestion, null));
                }
            }
            surveyAnswerQuestionsRepository.saveAll(surveyAnswerQuestionsList);
        }
    }

    private void processPreviousQuestion(List<SurveyAnswerDetailResponse> result, SurveyQuestionType surveyQuestionType, String currentQuestionNm, List<SurveyAnswerDetailResponse.AnswerCommand> answerCommandList, int totalCnt) {
        for (SurveyAnswerDetailResponse.AnswerCommand answerCommand : answerCommandList) {
            answerCommand.updatePercent(totalCnt);
        }

        if (currentQuestionNm != null) {
            result.add(SurveyAnswerDetailResponse.builder()
                                                 .surveyQuestionType(surveyQuestionType)
                                                 .questionNm(currentQuestionNm)
                                                 .answers(answerCommandList)
                                                 .build());
        }
    }

    private void processAnswer(SurveyDetailAnswerCommand surveyDetailAnswerCommand, List<SurveyAnswerDetailResponse.AnswerCommand> answerCommandList) {
        if (SurveyQuestionType.SINGLE_CHOICE == surveyDetailAnswerCommand.surveyQuestionType() || SurveyQuestionType.MULTIPLE_CHOICE == surveyDetailAnswerCommand.surveyQuestionType()) {
            addChoiceAnswer(surveyDetailAnswerCommand, answerCommandList);
        }
        else if (SurveyQuestionType.SHORT_ANSWER == surveyDetailAnswerCommand.surveyQuestionType() || SurveyQuestionType.LONG_ANSWER == surveyDetailAnswerCommand.surveyQuestionType()) {
            addShortAnswer(surveyDetailAnswerCommand, answerCommandList);
        }
    }

    private void addChoiceAnswer(SurveyDetailAnswerCommand surveyDetailAnswerCommand, List<SurveyAnswerDetailResponse.AnswerCommand> answerCommandList) {
        int count = surveyDetailAnswerCommand.selectedCnt().intValue();
        answerCommandList.add(SurveyAnswerDetailResponse.AnswerCommand.builder()
                                                                      .content("")
                                                                      .optionNm(surveyDetailAnswerCommand.optionContent())
                                                                      .count(count)
                                                                      .build());
    }

    private void addShortAnswer(SurveyDetailAnswerCommand surveyDetailAnswerCommand, List<SurveyAnswerDetailResponse.AnswerCommand> answerCommandList) {
        String answerContents = surveyDetailAnswerCommand.answerContents();
        if (answerContents != null && !answerContents.isEmpty()) {
            String[] individualAnswers = answerContents.replace("[", "").replace("]", "").replace("\"", "").split(",");
            for (String individualAnswer : individualAnswers) {
                answerCommandList.add(SurveyAnswerDetailResponse.AnswerCommand.builder()
                                                                              .content(individualAnswer.trim())
                                                                              .optionNm("")
                                                                              .build());
            }
        }
    }
}

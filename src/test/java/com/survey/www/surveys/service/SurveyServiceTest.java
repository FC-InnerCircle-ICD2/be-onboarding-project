package com.survey.www.surveys.service;

import com.survey.www.accounts.code.RoleType;
import com.survey.www.accounts.domain.Account;
import com.survey.www.accounts.repository.AccountRepository;
import com.survey.www.commons.doc.MyRestDoc;
import com.survey.www.surveys.code.SurveyQuestionType;
import com.survey.www.surveys.domain.Survey;
import com.survey.www.surveys.domain.SurveyQuestionOptions;
import com.survey.www.surveys.domain.SurveyQuestions;
import com.survey.www.surveys.dto.request.SurveyCreateRequest;
import com.survey.www.surveys.dto.request.SurveyUpdateRequest;
import com.survey.www.surveys.dto.response.SurveyCreateResponse;
import com.survey.www.surveys.dto.response.SurveyQuestionDetailResponse;
import com.survey.www.surveys.dto.response.SurveyUpdateResponse;
import com.survey.www.surveys.repository.SurveyQuestionsOptionsRepository;
import com.survey.www.surveys.repository.SurveyQuestionsRepository;
import com.survey.www.surveys.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "classpath:db/clean.sql")
@ActiveProfiles("local")
@Transactional
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SurveyServiceTest extends MyRestDoc {
    @Autowired private SurveyService surveyService;
    @Autowired private AccountRepository accountRepository;
    @Autowired private SurveyRepository surveyRepository;
    @Autowired private SurveyQuestionsRepository surveyQuestionsRepository;
    @Autowired private SurveyQuestionsOptionsRepository surveyQuestionsOptionsRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        createData();
    }

    @WithUserDetails(value = "01099841511", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("설문조사 생성 API")
    void createQuestion() {
        // given
        List<SurveyCreateRequest.SurveyQuestionOptionCommand> surveyQuestionOptionRequests1 = List.of(
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("동의합니다.").isDeleted(Boolean.FALSE).build()
        );

        List<SurveyCreateRequest.SurveyQuestionOptionCommand> surveyQuestionOptionRequests2 = List.of(
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("동의합니다.").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("동의하지 않습니다.").isDeleted(Boolean.FALSE).build()
        );

        List<SurveyCreateRequest.SurveyQuestionOptionCommand> surveyQuestionOptionRequests6 = List.of(
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("예, 참여하겠습니다.").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("아니오, 참여하지 않겠습니다. (하단의 사유 관련 질문 필수 응답)").isDeleted(Boolean.FALSE).build()
        );

        List<SurveyCreateRequest.SurveyQuestionOptionCommand> surveyQuestionOptionRequests7 = List.of(
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("고등학교 졸업").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("대학교 재학").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("대학교 졸업 예정").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("대학교 졸업").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("대학원 재학").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("대학원 졸업 예정").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("대학원 졸업").isDeleted(Boolean.FALSE).build()
        );

        List<SurveyCreateRequest.SurveyQuestionOptionCommand> surveyQuestionOptionRequests8 = List.of(
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("내일배움카드 보유").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("K-디지털트레이닝패스 보유").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("무 (발급 진행 중)").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("무 (미신청)").isDeleted(Boolean.FALSE).build()
        );

        List<SurveyCreateRequest.SurveyQuestionOptionCommand> surveyQuestionOptionRequests10 = List.of(
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("실무 역량 향상이 기대되는 커리큘럼").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("검증된 실력의 강사진").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("커리어 관련 지원 (자기소개서, 포트폴리오, 면접 준비 등의 커리어 서비스)").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("이상적인 실무를 경험할 수 있는 팀 프로젝트").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("팀원들과 협업할 수 있는 환경").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("꼼꼼하게 관리해주는 과정 운영진").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("패스트캠퍼스의 공신력").isDeleted(Boolean.FALSE).build(),
                SurveyCreateRequest.SurveyQuestionOptionCommand.builder().content("기타").isDeleted(Boolean.FALSE).build()
        );

        SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest1 =
                SurveyCreateRequest.SurveyQuestionCommand.builder()
                                                         .questionNm("[필수] 개인정보 수집 및 이용 동의")
                                                         .description(generateDescriptionForSurveyQuestions1())
                                                         .surveyQuestionType(SurveyQuestionType.SINGLE_CHOICE)
                                                         .isRequired(Boolean.TRUE)
                                                         .surveyQuestionOptions(surveyQuestionOptionRequests1)
                                                         .build();

        SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest2 =
                SurveyCreateRequest.SurveyQuestionCommand.builder()
                                                         .questionNm("[선택] 개인정보 마케팅 활용 동의")
                                                         .description(generateDescriptionForSurveyQuestions2())
                                                         .surveyQuestionType(SurveyQuestionType.SINGLE_CHOICE)
                                                         .isRequired(Boolean.FALSE)
                                                         .surveyQuestionOptions(surveyQuestionOptionRequests2)
                                                         .build();

        SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest3 =
                SurveyCreateRequest.SurveyQuestionCommand.builder()
                                                         .questionNm("성함을 작성해 주세요.")
                                                         .description("")
                                                         .surveyQuestionType(SurveyQuestionType.SHORT_ANSWER)
                                                         .isRequired(Boolean.FALSE)
                                                         .surveyQuestionOptions(List.of())
                                                         .build();

        SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest4 =
                SurveyCreateRequest.SurveyQuestionCommand.builder()
                                                         .questionNm("연락처를 작성해 주세요.")
                                                         .description("ex) 010-0000-0000")
                                                         .surveyQuestionType(SurveyQuestionType.SHORT_ANSWER)
                                                         .isRequired(Boolean.TRUE)
                                                         .surveyQuestionOptions(List.of())
                                                         .build();

        SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest5 =
                SurveyCreateRequest.SurveyQuestionCommand.builder()
                                                         .questionNm("이메일 주소를 작성해 주세요.")
                                                         .description("")
                                                         .surveyQuestionType(SurveyQuestionType.SHORT_ANSWER)
                                                         .isRequired(Boolean.TRUE)
                                                         .surveyQuestionOptions(List.of())
                                                         .build();

        SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest6 =
                SurveyCreateRequest.SurveyQuestionCommand.builder()
                                                         .questionNm("최종 과정 합류 의사를 선택해 주세요.")
                                                         .description("")
                                                         .surveyQuestionType(SurveyQuestionType.SINGLE_CHOICE)
                                                         .isRequired(Boolean.TRUE)
                                                         .surveyQuestionOptions(surveyQuestionOptionRequests6)
                                                         .build();

        SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest7 =
                SurveyCreateRequest.SurveyQuestionCommand.builder()
                                                         .questionNm("학력 사항을 선택해 주세요.")
                                                         .description("* 졸업 예정자란, 모집공고일을 기준으로 재학 중에 있으나 마지막 학기를 다니는 중이거나, 마지막 학기를 마쳤지만 졸업은 안한 상태인 자를 의미합니다.")
                                                         .surveyQuestionType(SurveyQuestionType.SINGLE_CHOICE)
                                                         .isRequired(Boolean.TRUE)
                                                         .surveyQuestionOptions(surveyQuestionOptionRequests7)
                                                         .build();

        SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest8 =
                SurveyCreateRequest.SurveyQuestionCommand.builder()
                                                         .questionNm("내일배움카드 혹은 K-디지털트레이닝패스 유무를 선택해 주세요.")
                                                         .description(generateDescriptionForSurveyQuestions8())
                                                         .surveyQuestionType(SurveyQuestionType.SINGLE_CHOICE)
                                                         .isRequired(Boolean.TRUE)
                                                         .surveyQuestionOptions(surveyQuestionOptionRequests8)
                                                         .build();

        SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest9 =
                SurveyCreateRequest.SurveyQuestionCommand.builder()
                                                         .questionNm("경력 사항을 입력해 주세요.")
                                                         .description(generateDescriptionForSurveyQuestions9())
                                                         .surveyQuestionType(SurveyQuestionType.LONG_ANSWER)
                                                         .isRequired(Boolean.TRUE)
                                                         .surveyQuestionOptions(List.of())
                                                         .build();

        SurveyCreateRequest.SurveyQuestionCommand surveyQuestionRequest10 =
                SurveyCreateRequest.SurveyQuestionCommand.builder()
                                                         .questionNm("본 교육 과정을 지원하게 된 사유는 무엇인가요?")
                                                         .description("")
                                                         .surveyQuestionType(SurveyQuestionType.MULTIPLE_CHOICE)
                                                         .isRequired(Boolean.TRUE)
                                                         .surveyQuestionOptions(surveyQuestionOptionRequests10)
                                                         .build();

        List<SurveyCreateRequest.SurveyQuestionCommand> surveyQuestionRequestList = List.of(
                surveyQuestionRequest1,
                surveyQuestionRequest2,
                surveyQuestionRequest3,
                surveyQuestionRequest4,
                surveyQuestionRequest5,
                surveyQuestionRequest6,
                surveyQuestionRequest7,
                surveyQuestionRequest8,
                surveyQuestionRequest9,
                surveyQuestionRequest10
        );

        SurveyCreateRequest surveyCreateRequest = SurveyCreateRequest.builder()
                .surveyNm("[패스트캠퍼스] INNER CIRCLE 풀스택 개발 코스 사전 조사")
                .description(generateDescriptionForSurvey())
                .surveyQuestions(surveyQuestionRequestList)
                .build();

        // when
        SurveyCreateResponse surveyCreateResponse = surveyService.createQuestion(surveyCreateRequest);

        // then
        assertThat(surveyCreateResponse.id()).isNotNull();
    }

    @Test
    @DisplayName("설문조사 상세 조회 TEST")
    void detailQuestion() {
        // given
        Long surveyId = 1L;

        // when
        SurveyQuestionDetailResponse surveyQuestionDetailResponse = surveyService.detailQuestion(surveyId);

        // then
        assertThat(surveyQuestionDetailResponse.getSurveyNm()).isEqualTo("[패스트캠퍼스] INNER CIRCLE 풀스택 개발 코스 사전 조사");
        assertThat(surveyQuestionDetailResponse.getDescription()).isNotNull();
        System.out.println(surveyQuestionDetailResponse);
    }

    @Test
    @DisplayName("설문조사 수정 API")
    void update() {
        // given
        Long surveyId = 1L;

        List<SurveyUpdateRequest.SurveyQuestionOptionCommand> surveyQuestionOptions1 = List.of(
                SurveyUpdateRequest.SurveyQuestionOptionCommand.builder().id(1L).content("동의합니다.1111").isDeleted(Boolean.FALSE).build()
        );

        List<SurveyUpdateRequest.SurveyQuestionCommand> surveyQuestions = List.of(
                SurveyUpdateRequest.SurveyQuestionCommand.builder().id(1L).questionNm("[필수] 개인정보 수집 및 이용 동의1111").description(generateDescriptionForSurveyQuestions1()+"1111").surveyQuestionType(SurveyQuestionType.SINGLE_CHOICE).isRequired(Boolean.TRUE).isDeleted(Boolean.FALSE).surveyQuestionOptions(surveyQuestionOptions1).build()
        );

        SurveyUpdateRequest surveyUpdateRequest = SurveyUpdateRequest.builder()
                                                                     .surveyNm("[패스트캠퍼스] INNER CIRCLE 풀스택 개발 코스 사전 조사1111")
                                                                     .description(generateDescriptionForSurvey() + "1111")
                                                                     .isDeleted(Boolean.FALSE)
                                                                     .surveyQuestions(surveyQuestions)
                                                                     .build();

        // when
        SurveyUpdateResponse surveyUpdateResponse = surveyService.update(surveyId, surveyUpdateRequest);

        // then
        assertThat(surveyUpdateResponse.id()).isNotNull();
    }

    private void createData() {
        Account account = Account.builder().roleType(RoleType.WRITER).phone("01099841511").userPw(passwordEncoder.encode("1234")).build();
        Account saveAccount = accountRepository.save(account);

        Survey survey = Survey.builder().surveyNm("[패스트캠퍼스] INNER CIRCLE 풀스택 개발 코스 사전 조사").description(generateDescriptionForSurvey()).isDeleted(Boolean.FALSE).account(saveAccount).build();
        Survey saveSurvey = surveyRepository.save(survey);

        // 질문1) [필수] 개인정보 수집 및 이용 동의
        SurveyQuestions surveyQuestions1 = SurveyQuestions.builder().questionNm("[필수] 개인정보 수집 및 이용 동의").description(generateDescriptionForSurveyQuestions1()).surveyQuestionType(SurveyQuestionType.SINGLE_CHOICE).isRequired(Boolean.TRUE).isDeleted(Boolean.TRUE).survey(saveSurvey).build();
        List<SurveyQuestionOptions> surveyQuestionOptions1 = List.of(
                SurveyQuestionOptions.builder().content("동의합니다.").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions1).build()
        );
        surveyQuestionsRepository.save(surveyQuestions1);
        surveyQuestionsOptionsRepository.saveAll(surveyQuestionOptions1);

        // 질문2) [선택] 개인정보 마케팅 활용 동의
        SurveyQuestions surveyQuestions2 = SurveyQuestions.builder().questionNm("[선택] 개인정보 마케팅 활용 동의").description(generateDescriptionForSurveyQuestions2()).surveyQuestionType(SurveyQuestionType.SINGLE_CHOICE).isRequired(Boolean.FALSE).isDeleted(Boolean.TRUE).survey(saveSurvey).build();
        List<SurveyQuestionOptions> surveyQuestionOptions2 = List.of(
                SurveyQuestionOptions.builder().content("동의합니다.").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions2).build(),
                SurveyQuestionOptions.builder().content("동의하지 않습니다.").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions2).build()
        );
        surveyQuestionsRepository.save(surveyQuestions2);
        surveyQuestionsOptionsRepository.saveAll(surveyQuestionOptions2);

        // 질문3) 성함을 작성해 주세요.
        SurveyQuestions surveyQuestions3 = SurveyQuestions.builder().questionNm("성함을 작성해 주세요.").description("").surveyQuestionType(SurveyQuestionType.SHORT_ANSWER).isRequired(Boolean.FALSE).isDeleted(Boolean.TRUE).survey(saveSurvey).build();
        surveyQuestionsRepository.save(surveyQuestions3);

        // 질문4) 연락처를 작성해 주세요.
        SurveyQuestions surveyQuestions4 = SurveyQuestions.builder().questionNm("연락처를 작성해 주세요.").description("ex) 010-0000-0000").surveyQuestionType(SurveyQuestionType.SHORT_ANSWER).isRequired(Boolean.TRUE).isDeleted(Boolean.TRUE).survey(saveSurvey).build();
        surveyQuestionsRepository.save(surveyQuestions4);

        // 질문5) 이메일 주소를 작성해 주세요.
        SurveyQuestions surveyQuestions5 = SurveyQuestions.builder().questionNm("이메일 주소를 작성해 주세요.").description("").surveyQuestionType(SurveyQuestionType.SHORT_ANSWER).isRequired(Boolean.TRUE).isDeleted(Boolean.TRUE).survey(saveSurvey).build();
        surveyQuestionsRepository.save(surveyQuestions5);

        // 질문6) 최종 과정 합류 의사를 선택해 주세요.
        SurveyQuestions surveyQuestions6 = SurveyQuestions.builder().questionNm("최종 과정 합류 의사를 선택해 주세요.").description("").surveyQuestionType(SurveyQuestionType.SINGLE_CHOICE).isRequired(Boolean.TRUE).isDeleted(Boolean.TRUE).survey(saveSurvey).build();
        List<SurveyQuestionOptions> surveyQuestionOptions6 = List.of(
                SurveyQuestionOptions.builder().content("예, 참여하겠습니다.").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions6).build(),
                SurveyQuestionOptions.builder().content("아니오, 참여하지 않겠습니다. (하단의 사유 관련 질문 필수 응답)").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions6).build()
        );
        surveyQuestionsRepository.save(surveyQuestions6);
        surveyQuestionsOptionsRepository.saveAll(surveyQuestionOptions6);

        // 질문7) 학력 사항을 선택해 주세요.
        SurveyQuestions surveyQuestions7 = SurveyQuestions.builder().questionNm("학력 사항을 선택해 주세요.").description("* 졸업 예정자란, 모집공고일을 기준으로 재학 중에 있으나 마지막 학기를 다니는 중이거나, 마지막 학기를 마쳤지만 졸업은 안한 상태인 자를 의미합니다.").surveyQuestionType(SurveyQuestionType.SINGLE_CHOICE).isRequired(Boolean.TRUE).isDeleted(Boolean.TRUE).survey(saveSurvey).build();
        List<SurveyQuestionOptions> surveyQuestionOptions7 = List.of(
                SurveyQuestionOptions.builder().content("고등학교 졸업").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions7).build(),
                SurveyQuestionOptions.builder().content("대학교 재학").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions7).build(),
                SurveyQuestionOptions.builder().content("대학교 졸업 예정").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions7).build(),
                SurveyQuestionOptions.builder().content("대학교 졸업").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions7).build(),
                SurveyQuestionOptions.builder().content("대학원 재학").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions7).build(),
                SurveyQuestionOptions.builder().content("대학원 졸업 예정").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions7).build(),
                SurveyQuestionOptions.builder().content("대학원 졸업").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions7).build()
        );
        surveyQuestionsRepository.save(surveyQuestions7);
        surveyQuestionsOptionsRepository.saveAll(surveyQuestionOptions7);

        // 질문8) 내일배움카드 혹은 K-디지털트레이닝패스 유무를 선택해 주세요.
        SurveyQuestions surveyQuestions8 = SurveyQuestions.builder().questionNm("내일배움카드 혹은 K-디지털트레이닝패스 유무를 선택해 주세요.").description(generateDescriptionForSurveyQuestions8()).surveyQuestionType(SurveyQuestionType.SINGLE_CHOICE).isRequired(Boolean.TRUE).isDeleted(Boolean.TRUE).survey(saveSurvey).build();
        List<SurveyQuestionOptions> surveyQuestionOptions8 = List.of(
                SurveyQuestionOptions.builder().content("내일배움카드 보유").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions8).build(),
                SurveyQuestionOptions.builder().content("K-디지털트레이닝패스 보유").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions8).build(),
                SurveyQuestionOptions.builder().content("무 (발급 진행 중)").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions8).build(),
                SurveyQuestionOptions.builder().content("무 (미신청)").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions8).build()
        );
        surveyQuestionsRepository.save(surveyQuestions8);
        surveyQuestionsOptionsRepository.saveAll(surveyQuestionOptions8);

        // 질문9) 경력 사항을 입력해 주세요.
        SurveyQuestions surveyQuestions9 = SurveyQuestions.builder().questionNm("경력 사항을 입력해 주세요.").description(generateDescriptionForSurveyQuestions9()).surveyQuestionType(SurveyQuestionType.LONG_ANSWER).isRequired(Boolean.TRUE).isDeleted(Boolean.TRUE).survey(saveSurvey).build();
        surveyQuestionsRepository.save(surveyQuestions9);

        // 질문10) 본 교육 과정을 지원하게 된 사유는 무엇인가요?
        SurveyQuestions surveyQuestions10 = SurveyQuestions.builder().questionNm("본 교육 과정을 지원하게 된 사유는 무엇인가요?").description("").surveyQuestionType(SurveyQuestionType.MULTIPLE_CHOICE).isRequired(Boolean.TRUE).isDeleted(Boolean.TRUE).survey(saveSurvey).build();
        List<SurveyQuestionOptions> surveyQuestionOptions10 = List.of(
                SurveyQuestionOptions.builder().content("실무 역량 향상이 기대되는 커리큘럼").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions10).build(),
                SurveyQuestionOptions.builder().content("검증된 실력의 강사진").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions10).build(),
                SurveyQuestionOptions.builder().content("커리어 관련 지원 (자기소개서, 포트폴리오, 면접 준비 등의 커리어 서비스)").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions10).build(),
                SurveyQuestionOptions.builder().content("이상적인 실무를 경험할 수 있는 팀 프로젝트").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions10).build(),
                SurveyQuestionOptions.builder().content("팀원들과 협업할 수 있는 환경").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions10).build(),
                SurveyQuestionOptions.builder().content("꼼꼼하게 관리해주는 과정 운영진").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions10).build(),
                SurveyQuestionOptions.builder().content("패스트캠퍼스의 공신력").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions10).build(),
                SurveyQuestionOptions.builder().content("기타").isDeleted(Boolean.FALSE).surveyQuestions(surveyQuestions10).build()
        );
        surveyQuestionsRepository.save(surveyQuestions10);
        surveyQuestionsOptionsRepository.saveAll(surveyQuestionOptions10);
    }

    private String generateDescriptionForSurvey() {
        return """
                안녕하세요? 😊 패스트캠퍼스입니다.
                INNER CIRCLE 풀스택 개발 Course에 합격하신 것을 진심으로 축하드립니다.
                
                과정 시작에 앞서, 수강생 여러분들의 최종 합류 의사 확인과 준비 현황 파악하고
                접수 과정에서 통일되지 않았거나, 잘못된 정보를 수정하기 위한 설문을 진행하고자 합니다.
                
                수강생 여러분의 현황을 파악하기 위함이니, 정확히 작성해주실 것을 부탁 드립니다.📝
                
                ※ 기간 내 미응답시 합격이 취소될 수 있습니다. 과정 철회 시에도 설문 응답 필수입니다.
                """;
    }

    private String generateDescriptionForSurveyQuestions1() {
        return """
                패스트캠퍼스는 귀하의 개인정보를 수집 · 이용하기 위하여 아래와 같이 「개인정보보호법」 제15조에 따라 동의를 구합니다.
                
                1. 수집·이용 목적: 참가 신청자 확인를 위한 개인정보 수집
                2. 수집하는 개인정보의 항목: 성함, 생년월일, 연락처, 이메일 주소, 학력 사항, 경력 사항
                3. 보유 및 이용 기간: 수집일로부터 1년
                
                * 본 설문을 통해 수집하는 개인 정보는 참가 신청자를 확인하기 위한 용도로만 활용되며, 기타 용도로는 절대 사용되지 않음을 알려드립니다.
                """;
    }

    private String generateDescriptionForSurveyQuestions2() {
        return """
                - 수집 및 이용 목적 : 상품 및 서비스 추천, 상품 및 서비스 안내, 행사 및 이벤트 등 안내
                - 수집 항목 : 성함, 이메일 주소, 연락처, 마케팅 정보 수신 동의 여부
                - 이용 및 보유 기간 : 교육 지원 취소 또는 동의 철회 시까지
                
                ※ 지원자님께서는 [선택] 개인정보 마케팅 활용 동의에 거부할 수 있습니다.
                단, 거부할 경우 상품 및 서비스 추천이나 안내를 받을 수 없고, 각종 행사 및 이벤트에 참여가 제한될 수 있음을 안내해 드립니다.
                """;
    }

    private String generateDescriptionForSurveyQuestions8() {
        return """
                * 본 과정은 내일배움카드 혹은 K-디지털트레이닝패스를 보유하셔야 수강 가능합니다. 빠르게 발급해 주세요.
                * K-디지털트레이닝패스는 소득, 연령 등 요건과 무관하게 현재 재직 중이라면 발급가능하며, 본 재직자 과정을 위해 신규 개설되었습니다. 기존 내일배움카드를 보유하고 계신 분이라면, K-디지털트레이닝패스를 추가 발급 받지 않으셔도 본 교육 참여가 가능합니다.
                * 고용24 접속 → 공인인증서로 로그인 → 카드 유효기간 확인이 필요합니다.
                * 자세한 사항은 거주하시 지역의 관할 고용센터에 문의해 주시길 바랍니다.
                """;
    }

    private String generateDescriptionForSurveyQuestions9() {
        return """
                * 회사명, 직무, 근무기간 순서로 작성해 주세요.
                * 단, 최근 경력을 먼저 적어주시고 슬래쉬(/)로 구분해 주시길 바랍니다.
                
                ex) 패스트캠퍼스, 프로덕트 매니저, 1년 5개월 / 패스트트랙아시아, UXUI 디자이너, 6개월
                * 경력사항이 없으실 경우 '없음'으로 기재 부탁 드립니다.
                """;
    }
}

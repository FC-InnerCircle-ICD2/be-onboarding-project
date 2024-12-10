package study.crispin.surveycore.service

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import study.crispin.surveycore.domain.Form
import study.crispin.surveycore.domain.Survey
import study.crispin.surveycore.domain.SurveyItem
import study.crispin.surveycore.fake.SurveyFakeAdaptor

class SurveyServiceTest : DescribeSpec({

    lateinit var surveyService: SurveyUseCase

    beforeTest {
        surveyService = SurveyService(
            surveyPort = SurveyFakeAdaptor()
        )
    }

    describe("설문조사 서비스 테스트") {

        describe("설문조사 생성 테스트") {

            context("정상적인 생성 요청일 때") {

                it("설문조사를 생성 할 수 있어야 한다.") {
                    // given
                    val request = CreateSurveyUseCase.Request(
                        "관심사 조사",
                        "성별에 따른 관심사 조사",
                        listOf(
                            SurveyItem(
                                "이름",
                                "성함을 입력해주세요.",
                                Form.ShortInput,
                                true
                            ),
                            SurveyItem(
                                "나이",
                                "나이을 입력해주세요.",
                                Form.ShortInput,
                                false
                            ),
                            SurveyItem(
                                "관심사",
                                "관심 있는 분야를 모두 선택해주세요.",
                                Form.MultiSelect(listOf("스포츠", "음약", "기술")),
                                true
                            )
                        )
                    )

                    // when
                    val actual: Survey = surveyService.createSurvey(request)

                    // then
                    assertSoftly {
                        actual.name shouldBe "관심사 조사"
                        actual.items shouldHaveSize 3
                    }
                }
            }
        }
    }
})

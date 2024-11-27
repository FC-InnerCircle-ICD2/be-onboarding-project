package study.crispin.surveyinfra.adaptor

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldNotBe
import study.crispin.surveyinfra.adaptor.dto.FormDto
import study.crispin.surveyinfra.adaptor.dto.SurveyDto
import study.crispin.surveyinfra.adaptor.dto.SurveyItemDto
import study.crispin.surveyinfra.port.SaveSurveyPort
import study.crispin.surveyinfra.port.SurveyPort
import study.crispin.surveyinfra.repository.SurveyItemMemoryRepository
import study.crispin.surveyinfra.repository.SurveyItemRepository
import study.crispin.surveyinfra.repository.SurveyMemoryRepository
import study.crispin.surveyinfra.repository.SurveyRepository

class SurveyAdaptorTest : DescribeSpec({

    lateinit var surveyRepository: SurveyRepository
    lateinit var surveyItemRepository: SurveyItemRepository
    lateinit var surveyAdaptor: SurveyPort

    beforeTest {
        surveyRepository = SurveyMemoryRepository()
        surveyItemRepository = SurveyItemMemoryRepository()
        surveyAdaptor = SurveyAdaptor(surveyRepository, surveyItemRepository)
    }

    describe("설문조사 레파지토리 테스트") {

        describe("설문조사 저장 테스트") {

            context("정상적인 저장 요청일 때 ") {

                it("설문조사를 저장 할 수 있어야 한다.") {
                    // given
                    val request = SaveSurveyPort.Request(
                        "관심사 조사",
                        "성별에 따른 관심사 조사",
                        listOf(
                            SurveyItemDto(
                                "이름",
                                "성함을 입력해주세요.",
                                FormDto.ShortInput,
                                true,
                            ),
                            SurveyItemDto(
                                "나이",
                                "나이을 입력해주세요.",
                                FormDto.ShortInput,
                                false,
                            ),
                            SurveyItemDto(
                                "관심사",
                                "관심 있는 분야를 모두 선택해주세요.",
                                FormDto.MultiSelect(listOf("스포츠", "음약", "기술")),
                                true,
                            )
                        )
                    )
                    // when
                    val actual: SurveyDto = surveyAdaptor.save(request)

                    // then
                    assertSoftly {
                        actual.id shouldNotBe null
                        actual.items shouldHaveSize 3
                    }
                }
            }
        }
    }
})

package com.ic.surveydata

import com.ic.surveydata.form.entity.SurveyFormEntity
import com.ic.surveydata.form.entity.SurveyItemEntity
import com.ic.surveydata.form.entity.SurveyOptionEntity
import com.ic.surveydata.form.repositry.SurveyFormRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import survey.type.ItemType
import survey.util.UuidGeneratorUtil

@SpringBootTest
@TestPropertySource(properties = ["spring.jpa.hibernate.ddl-auto=create-drop"])
class SurveyFormEntityTests
    @Autowired
    constructor(
        private val surveyFormRepository: SurveyFormRepository,
    ) : StringSpec({

            "SurveyFormEntity 가 정상적으로 생성 되는 지 테스트 (Survey, SurveyItem, SurveyOption)- 단순 테스트" {
                val surveyOption1 =
                    SurveyOptionEntity(
                        id = UuidGeneratorUtil.generateUuid(),
                        name = "Option 1",
                    )
                val surveyOption2 =
                    SurveyOptionEntity(
                        id = UuidGeneratorUtil.generateUuid(),
                        name = "Option 2",
                    )

                val surveyItem =
                    SurveyItemEntity(
                        id = UuidGeneratorUtil.generateUuid(),
                        name = "Survey Item 1",
                        isRequired = true,
                        description = "Description for Survey Item 1",
                        type = ItemType.SINGLE_SELECT,
                    )
                surveyItem.surveyOptions.addAll(listOf(surveyOption1, surveyOption2))
                surveyOption1.surveyItemEntity = surveyItem
                surveyOption2.surveyItemEntity = surveyItem

                val surveyForm =
                    SurveyFormEntity(
                        id = UuidGeneratorUtil.generateUuid(),
                        title = "Simple Survey Form",
                        description = "This is a simple survey form",
                        version = 1,
                    )
                surveyForm.addSurveyItems(listOf(surveyItem))

                val savedSurveyForm = surveyFormRepository.save(surveyForm)
                val fetchedSurveyForm = surveyFormRepository.findById(savedSurveyForm.id).orElse(null)

                fetchedSurveyForm.id shouldBe savedSurveyForm.id

                println("SURVEY FETCHED FORM !! $fetchedSurveyForm")

                fetchedSurveyForm.surveyItems shouldHaveSize 1
                fetchedSurveyForm.surveyItems.first().surveyOptions shouldHaveSize 2

                fetchedSurveyForm.surveyItems.first().surveyOptions.toList()[0].name shouldBe "Option 1"
                fetchedSurveyForm.surveyItems.first().surveyOptions.toList()[1].name shouldBe "Option 2"
            }

            "SurveyFormEntity 가 정상적으로 생성 되는 지 테스트 (Survey, SurveyItem, SurveyOption)- 복잡 테스트" {
                val surveyOptionsForItem1 =
                    (1..3).map {
                        SurveyOptionEntity(
                            id = UuidGeneratorUtil.generateUuid(),
                            name = "Option $it",
                        )
                    }

                val surveyOptionsForItem2 =
                    (1..2).map {
                        SurveyOptionEntity(
                            id = UuidGeneratorUtil.generateUuid(),
                            name = "Option $it for Item 2",
                        )
                    }

                val surveyItem1 =
                    SurveyItemEntity(
                        id = UuidGeneratorUtil.generateUuid(),
                        name = "Survey Item 1",
                        isRequired = true,
                        description = "Description for Survey Item 1",
                        type = ItemType.MULTIPLE_SELECT,
                    )
                val surveyItem2 =
                    SurveyItemEntity(
                        id = UuidGeneratorUtil.generateUuid(),
                        name = "Survey Item 2",
                        isRequired = false,
                        description = "Description for Survey Item 2",
                        type = ItemType.SINGLE_SELECT,
                    )

                surveyItem1.surveyOptions.addAll(surveyOptionsForItem1)
                surveyOptionsForItem1.forEach { it.surveyItemEntity = surveyItem1 }

                surveyItem2.surveyOptions.addAll(surveyOptionsForItem2)
                surveyOptionsForItem2.forEach { it.surveyItemEntity = surveyItem2 }

                val surveyForm =
                    SurveyFormEntity(
                        id = UuidGeneratorUtil.generateUuid(),
                        title = "Complex Survey Form",
                        description = "This is a complex survey form",
                        version = 2,
                    )
                surveyForm.addSurveyItems(listOf(surveyItem1, surveyItem2))

                val savedSurveyForm = surveyFormRepository.save(surveyForm)
                val fetchedSurveyForm = surveyFormRepository.findById(savedSurveyForm.id).orElse(null)

                fetchedSurveyForm.id shouldBe savedSurveyForm.id
                fetchedSurveyForm.surveyItems shouldHaveSize 2
                fetchedSurveyForm.surveyItems.find { it.name == "Survey Item 1" }!!.surveyOptions shouldHaveSize 3
                fetchedSurveyForm.surveyItems.find { it.name == "Survey Item 2" }!!.surveyOptions shouldHaveSize 2
            }
        })

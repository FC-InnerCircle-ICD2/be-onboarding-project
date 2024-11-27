package org.icd.surveyapi.support

import com.fasterxml.jackson.databind.ObjectMapper
import org.icd.surveyapi.support.filter.CachingFilter
import org.icd.surveycore.domain.support.value.BaseEnum
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
abstract class BaseControllerTest {

    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var context: WebApplicationContext

    @BeforeEach
    fun setUp(provider: RestDocumentationContextProvider) {
        mockMvc =
            MockMvcBuilders
                .webAppContextSetup(context)
                .apply {
                    preprocessRequest(Preprocessors.prettyPrint())
                    preprocessResponse(Preprocessors.prettyPrint())
                }
                .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
                .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true), CachingFilter())
                .build()
    }

    fun <T> generateEnumValues(
        clazz: Class<T>,
    ): String where T : Enum<T>, T : BaseEnum {
        val sb = StringBuilder()

        sb.append("\n")
        sb.append(clazz.simpleName)
        sb.append("\n")

        for (enumValue in clazz.enumConstants) {
            sb.append("${enumValue.name} : ${enumValue.description}\n")
            sb.append("\n")
        }

        return sb.trimEnd().toString()
    }
}
package com.ic.surveyapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.ic.surveyapi",
        "com.ic.surveydata",
    ],
)
class SurveyApiApplication

fun main(args: Array<String>) {
    runApplication<SurveyApiApplication>(*args)
}

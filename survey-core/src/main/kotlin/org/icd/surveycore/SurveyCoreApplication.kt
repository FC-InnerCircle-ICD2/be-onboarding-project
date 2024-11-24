package org.icd.surveycore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SurveyCoreApplication

fun main(args: Array<String>) {
    runApplication<SurveyCoreApplication>(*args)
}

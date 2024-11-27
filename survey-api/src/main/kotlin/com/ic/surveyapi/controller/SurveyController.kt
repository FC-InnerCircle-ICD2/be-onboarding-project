package com.ic.surveyapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import survey.common.ApiEndpointVersionPrefix

@RestController
@RequestMapping(ApiEndpointVersionPrefix.V1_API_PREFIX)
class SurveyController {

    @GetMapping("/ping")
    fun check(): String {
        return "pong !"
    }
}


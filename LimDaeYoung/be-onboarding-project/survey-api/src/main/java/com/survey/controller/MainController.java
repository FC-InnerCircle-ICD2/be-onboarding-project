package com.survey.controller;

import com.common.api.entity.SurveyEntity;
import com.survey.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class MainController {
    @Autowired
    MemberService memberService;

    @PostMapping("/save")
    public ResponseEntity<SurveyEntity> save(@RequestBody SurveyEntity survey) {
        return new ResponseEntity<SurveyEntity>(memberService.save(survey), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<SurveyEntity>> search() {
        List<SurveyEntity> survey = memberService.findAll();
        return new ResponseEntity<List<SurveyEntity>>(survey, HttpStatus.OK);
    }
}

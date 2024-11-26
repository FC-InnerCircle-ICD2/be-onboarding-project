package com.survey.service;

import com.common.api.entity.SurveyEntity;
import com.common.api.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private SurveyRepository surveyRepository;

    public List<SurveyEntity> findAll() {
        List<SurveyEntity> surveys = new ArrayList<>();
        surveyRepository.findAll().forEach(e -> surveys.add(e));
        return surveys;
    }

    public Optional<SurveyEntity> findById(Long mbrNo) {
        Optional<SurveyEntity> survey = surveyRepository.findById(mbrNo);
        return survey;
    }

    public void deleteById(Long mbrNo) {
        surveyRepository.deleteById(mbrNo);
    }

    public SurveyEntity save(SurveyEntity survey) {
        surveyRepository.save(survey);
        return survey;
    }

    //    public void updateById(Long id, Survey survey) {
    //        Optional<Survey> e = surveyRepository.findById(id);
    //
    //        if (e.isPresent()) {
    //            e.get().setId(survey.getId());
    //            e.get().setName(survey.getName());
    //            e.get().setItmes(survey.getItmes());
    //            surveyRepository.save(survey);
    //        }
    //    }
}

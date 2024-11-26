package com.survey.api.service;

import com.survey.api.constant.CommonConstant;
import com.survey.api.entity.SurveyEntity;
import com.survey.api.entity.SurveyItemEntity;
import com.survey.api.entity.SurveyOptionEntity;
import com.survey.api.exception.SurveyApiException;
import com.survey.api.form.SurveyItemUpdateForm;
import com.survey.api.form.SurveyOptionUpdateForm;
import com.survey.api.form.SurveyUpdateForm;
import com.survey.api.repository.SurveyItemRepository;
import com.survey.api.repository.SurveyOptionRepository;
import com.survey.api.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {
    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyItemRepository surveyItemRepository;


    @Autowired
    private SurveyOptionRepository surveyOptionRepository;

    public List<SurveyEntity> findAll() {
        List<SurveyEntity> surveys = new ArrayList<>();
        surveyRepository.findAll().forEach(e -> surveys.add(e));
        return surveys;
    }

    public Optional<SurveyEntity> findById(Long mbrNo) {
        Optional<SurveyEntity> survey = surveyRepository.findById(mbrNo);
        return survey;
    }

    public boolean existsSurveyById(Long id) {
        return surveyRepository.existsById(id);
    }

    public boolean existsSurveyItemById(Long id) {
        return surveyItemRepository.existsById(id);
    }

    public boolean existsSurveyOptionById(Long id) {
        return surveyOptionRepository.existsById(id);
    }

    public void deleteById(Long mbrNo) {
        surveyRepository.deleteById(mbrNo);
    }

    public SurveyEntity sureveySave(SurveyEntity survey) {
        surveyRepository.save(survey);
        return survey;
    }

    public SurveyItemEntity itemSave(SurveyItemEntity item) {
        surveyItemRepository.save(item);
        return item;
    }

    public SurveyOptionEntity optionSave(SurveyOptionEntity item) {
        surveyOptionRepository.save(item);
        return item;
    }

    @Transactional
    public void surveyUpdate(SurveyUpdateForm survey) {
        SurveyEntity surveyResult = sureveySave(new SurveyEntity(survey.getId(), survey.getName(), survey.getDescription(), CommonConstant.Y));

        if(survey.getItems() != null) {
            for (SurveyItemUpdateForm itemForm : survey.getItems()) {
                SurveyItemEntity itemEntity = null;

                if(CommonConstant.ACTION_TYPE_CREATE.equals(itemForm.getActionType())) {
                    itemEntity = itemSave(new SurveyItemEntity(itemForm.getItemName(), itemForm.getDescription(), itemForm.getItemType(), itemForm.isRequired(), CommonConstant.Y, surveyResult));
                } else if(CommonConstant.ACTION_TYPE_DELETE.equals(itemForm.getActionType())) {
                    itemEntity = itemSave(new SurveyItemEntity(itemForm.getId(), itemForm.getItemName(), itemForm.getDescription(), itemForm.getItemType(), itemForm.isRequired(), CommonConstant.N, surveyResult));
                } else {
                    itemEntity = itemSave(new SurveyItemEntity(itemForm.getId(), itemForm.getItemName(), itemForm.getDescription(), itemForm.getItemType(), itemForm.isRequired(), CommonConstant.Y, surveyResult));
                }

                if (CommonConstant.SINGLE_ITEM.equals(itemForm.getItemType()) || CommonConstant.MULTI_ITEM.equals(itemForm.getItemType())) {
                    if (itemForm.getOptionList() != null) {
                        for (SurveyOptionUpdateForm optionForm : itemForm.getOptionList()) {
                            if(CommonConstant.ACTION_TYPE_CREATE.equals(optionForm.getActionType()) && !CommonConstant.ACTION_TYPE_DELETE.equals(itemForm.getActionType())) {
                                optionSave(new SurveyOptionEntity(optionForm.getOptionName(), optionForm.getOptionOrder(), CommonConstant.Y, itemEntity));
                            } else if(CommonConstant.ACTION_TYPE_DELETE.equals(optionForm.getActionType()) || CommonConstant.ACTION_TYPE_DELETE.equals(itemForm.getActionType())) {
                                optionSave(new SurveyOptionEntity(optionForm.getId(), optionForm.getOptionName(), optionForm.getOptionOrder(), CommonConstant.N, itemEntity));
                            } else {
                                optionSave(new SurveyOptionEntity(optionForm.getId(), optionForm.getOptionName(), optionForm.getOptionOrder(), CommonConstant.Y, itemEntity));
                            }
                        }
                    }
                }
            }
        }
    }
}

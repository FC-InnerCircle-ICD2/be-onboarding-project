package com.survey.api.service;

import com.survey.api.constant.CommonConstant;
import com.survey.api.dto.SurveyResponseDto;
import com.survey.api.dto.SurveyResponseItemDto;
import com.survey.api.entity.*;
import com.survey.api.form.*;
import com.survey.api.repository.*;
import com.survey.api.util.ConvertUtil;
import com.survey.api.util.DateUtil;
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

    @Autowired
    private SurveyResponseRepository surveyResponseRepository;

    @Autowired
    private SurveyResponseItemRepository surveyResponseItemRepository;


    @Autowired
    private SurveyResponseOptionRepository surveyResponseOptionRepository;


    public List<SurveyEntity> findAll() {
        List<SurveyEntity> surveys = new ArrayList<>();
        surveyRepository.findAll().forEach(e -> surveys.add(e));
        return surveys;
    }

    public Optional<SurveyEntity> findById(Long mbrNo) {
        Optional<SurveyEntity> survey = surveyRepository.findById(mbrNo);
        return survey;
    }

    public SurveyItemEntity findItemByIdAndItemTypeAndSurvey(Long id, String itemType, SurveyEntity survey) {
        return surveyItemRepository.findByIdAndItemTypeAndSurvey(id, itemType, survey);
    }

    public boolean existsSurveyById(Long id) {
        return !surveyRepository.existsById(id);
    }

    public boolean existsSurveyItemById(Long id) {
        return !surveyItemRepository.existsById(id);
    }

    public boolean existsByIdAndSurvey(Long id, SurveyEntity survey) {
        return !surveyItemRepository.existsByIdAndSurvey(id, survey);
    }

    public boolean existsSurveyOptionById(Long id) {
        return !surveyOptionRepository.existsById(id);
    }

    public boolean existsSurveyOptionByIdAndItemId(Long id, SurveyItemEntity surveyItem) {
        return !surveyOptionRepository.existsByIdAndSurveyItem(id, surveyItem);
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

    public SurveyResponseEntity responseSave(SurveyResponseEntity survey) {
        surveyResponseRepository.save(survey);
        return survey;
    }

    public SurveyResponseItemEntity responseItemSave(SurveyResponseItemEntity item) {
        surveyResponseItemRepository.save(item);
        return item;
    }

    public SurveyResponseOptionEntity responseOptionSave(SurveyResponseOptionEntity item) {
        surveyResponseOptionRepository.save(item);
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

    public List<SurveyResponseDto> findResponsesBySurveyIdWithFilters(long id, String searchParam, int pageNumber) {
        //return surveyResponseRepository.findResponsesBySurveyIdWithFilters(id, searchParam, pageNumber);
        return surveyResponseRepository.findResponsesBySurveyIdWithFilters(id);
    }

    public List<SurveyResponseItemDto> findResponseItemByFilters(long id, String searchParam) {
        //return surveyResponseRepository.findResponsesBySurveyIdWithFilters(id, searchParam, pageNumber);
        return surveyResponseItemRepository.findResponseItemByFilters(id);
    }

    public String findResponseOptionByFilters(long id, String searchParam) {
        //return surveyResponseRepository.findResponsesBySurveyIdWithFilters(id, searchParam, pageNumber);
        return surveyResponseOptionRepository.findResponseOptionByFilters(id);
    }

    @Transactional
    public void surveyResponseSave(SurveyResponseForm survey) {
        SurveyResponseEntity surveyResult = responseSave(new SurveyResponseEntity(new SurveyEntity(survey.getId())));

        for (SurveyResponseItemForm itemForm : survey.getItems()) {
            String answer = "";

            if(CommonConstant.SHORT_ITEM.equals(itemForm.getReponseType()) || CommonConstant.LONG_ITEM.equals(itemForm.getReponseType())) {
                answer = (itemForm.getAnswer() != null && itemForm.getAnswer().length > 0 ? itemForm.getAnswer()[0] : "");
            }

            SurveyResponseItemEntity reponseItem = responseItemSave(new SurveyResponseItemEntity(answer, surveyResult, new SurveyItemEntity(itemForm.getId())));

            if(CommonConstant.MULTI_ITEM.equals(itemForm.getReponseType()) || CommonConstant.SINGLE_ITEM.equals(itemForm.getReponseType())) {
                for(String optionId : itemForm.getAnswer()) {
                    SurveyResponseOptionEntity reponseOption = responseOptionSave(new SurveyResponseOptionEntity(reponseItem, new SurveyOptionEntity(ConvertUtil.stringToLong(optionId))));


                }
            }
        }
    }
}

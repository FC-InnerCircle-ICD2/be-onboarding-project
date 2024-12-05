package com.survey.api.service;

import com.survey.api.constant.CommonConstant;
import com.survey.api.entity.*;
import com.survey.api.exception.SurveyApiException;
import com.survey.api.form.*;
import com.survey.api.repository.*;
import com.survey.api.util.ConvertUtil;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public long countBySurveyAndUseYn(SurveyEntity survey, String useYn) {
        return surveyItemRepository.countBySurveyAndUseYn(survey, useYn);
    }

    public SurveyItemEntity findItemByIdAndSurvey(Long id, SurveyEntity survey) {
        return surveyItemRepository.findByIdAndSurvey(id, survey);
    }

    public boolean existsItemByIdAndSurvey(Long id, SurveyEntity survey) {
        return !surveyItemRepository.existsItemByIdAndSurvey(id, survey);
    }


    public SurveyEntity findSurveyById(Long id) {
        return surveyRepository.findSurveyById(id);
    }

    public SurveyOptionEntity findOptionById(Long id) {
        return surveyOptionRepository.findOptionById(id);
    }

    public List<SurveyResponseOptionEntity> findResponseOptionById(Long id) {
        return surveyResponseOptionRepository.findByresponseItemId(id);
    }

    public boolean existsSurveyById(Long id) {
        return !surveyRepository.existsById(id);
    }


    public boolean existsSurveyOptionByIdAndItemId(Long id, SurveyItemEntity surveyItem) {
        return !surveyOptionRepository.existsByIdAndSurveyItem(id, surveyItem);
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
    public void save(SurveyForm survey) {
        //DB insert
        SurveyEntity surveyResult = sureveySave(new SurveyEntity(survey.getName(), survey.getDescription(), CommonConstant.Y));

        for(SurveyItemForm itemForm : survey.getItems()){
            SurveyItemEntity itemEntity = itemSave(new SurveyItemEntity( itemForm.getItemName(), itemForm.getDescription(), itemForm.getItemType(), itemForm.isRequired(), CommonConstant.Y, surveyResult));

            if(CommonConstant.SINGLE_ITEM.equals(itemForm.getItemType()) || CommonConstant.MULTI_ITEM.equals(itemForm.getItemType())) {
                if(itemForm.getOptionList() != null) {
                    for (SurveyOptionForm optionForm : itemForm.getOptionList()) {
                        optionSave(new SurveyOptionEntity(optionForm.getOptionName(), optionForm.getOptionOrder(), CommonConstant.Y, itemEntity));
                    }
                }
            }
        }
    }

    @Transactional
    public void surveyUpdate(SurveyUpdateForm survey) {
        SurveyEntity surveyResult = sureveySave(new SurveyEntity(survey.getId(), survey.getName(), survey.getDescription(), CommonConstant.Y));

        if(survey.getItems() != null) {
            for (SurveyUpdateItemForm itemForm : survey.getItems()) {
                SurveyItemEntity itemEntity = null;

                String isNotDelete = CommonConstant.Y;

                if(CommonConstant.ACTION_TYPE_DELETE.equals(itemForm.getActionType()) || CommonConstant.ACTION_TYPE_UPDATE.equals(itemForm.getActionType())) {
                    if(existsItemByIdAndSurvey(itemForm.getId(), new SurveyEntity(survey.getId()))) {
                        throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 설문 조사를 수정하려 합니다.");
                    }

                    if(CommonConstant.ACTION_TYPE_DELETE.equals(itemForm.getActionType())){
                        isNotDelete = CommonConstant.N;
                    }
                }

                itemEntity = itemSave(new SurveyItemEntity(itemForm.getId(), itemForm.getItemName(), itemForm.getDescription(), itemForm.getItemType(), itemForm.isRequired(), isNotDelete, surveyResult));

                if (CommonConstant.SINGLE_ITEM.equals(itemForm.getItemType()) || CommonConstant.MULTI_ITEM.equals(itemForm.getItemType())) {
                    if (itemForm.getOptionList() != null) {
                        for (SurveyUpdateOptionForm optionForm : itemForm.getOptionList()) {
                            isNotDelete = CommonConstant.Y;
                            if(CommonConstant.ACTION_TYPE_DELETE.equals(optionForm.getActionType()) || CommonConstant.ACTION_TYPE_UPDATE.equals(optionForm.getActionType())) {
                                if (existsSurveyOptionByIdAndItemId(optionForm.getId(), itemEntity)) {
                                    throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 선택지입니다.");
                                }

                                if(CommonConstant.ACTION_TYPE_DELETE.equals(optionForm.getActionType())){
                                    isNotDelete = CommonConstant.N;
                                }
                            }
                            optionSave(new SurveyOptionEntity(optionForm.getId(), optionForm.getOptionName(), optionForm.getOptionOrder(), isNotDelete, itemEntity));
                        }
                    }
                }
            }
        }
    }

    public List<SurveyResponseEntity> findResponsesBySurveyIdWithFilters(long id, String searchParam, int pageNumber) {
        Pageable pageable = (Pageable) PageRequest.of(pageNumber, 10, Sort.by("id").descending());
        return surveyResponseRepository.findResponsesBySurveyIdWithFilters(id, pageable);
    }

    @Transactional
    public void surveyResponseSave(SurveyResponseForm survey) {
        SurveyEntity surveyEntity = findSurveyById(survey.getId());

        SurveyResponseEntity surveyResult = responseSave(new SurveyResponseEntity(surveyEntity.getId(), surveyEntity.getName(), surveyEntity.getDescription()));

        if(countBySurveyAndUseYn(new SurveyEntity(survey.getId()), CommonConstant.Y) != survey.getItems().size()) {
            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "설문지와 응답 항목이 일치하지 않습니다.");
        }

        for (SurveyResponseItemForm itemResponseForm : survey.getItems()) {
            SurveyItemEntity itemEntity = findItemByIdAndSurvey(itemResponseForm.getId(), new SurveyEntity(survey.getId()));

            if (itemEntity == null) {
                throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 설문 조사 항목입니다.");
            }

            if(itemEntity.isRequired()) {
                switch(itemEntity.getItemType()) {
                    case CommonConstant.SHORT_ITEM:
                        if(StringUtils.isBlank(itemResponseForm.getShortAnswer())) {
                            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "필수 값에 대한 응답이 없습니다.");
                        }
                        break;
                    case CommonConstant.LONG_ITEM:
                        if(StringUtils.isBlank(itemResponseForm.getLongAnswer())) {
                            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "필수 값에 대한 응답이 없습니다.");
                        }
                        break;
                    case CommonConstant.MULTI_ITEM:
                    case CommonConstant.SINGLE_ITEM:
                        if(itemResponseForm.getSelectOptions() == null || itemResponseForm.getSelectOptions().length == 0) {
                            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "필수 값에 대한 응답이 없습니다.");
                        }
                        break;
                }
            }

            SurveyResponseItemEntity reponseItem = responseItemSave(new SurveyResponseItemEntity(itemResponseForm.getLongAnswer(), itemResponseForm.getShortAnswer(), surveyResult, itemEntity.getItemName(),  itemEntity.getDescription(), itemEntity.getItemType(), itemEntity.getUseYn(), itemEntity.isRequired()));

            if(CommonConstant.MULTI_ITEM.equals(itemResponseForm.getItemType()) || CommonConstant.SINGLE_ITEM.equals(itemResponseForm.getItemType())) {
                for(String optionId : itemResponseForm.getSelectOptions()) {
                    if (existsSurveyOptionByIdAndItemId(ConvertUtil.stringToLong(optionId), itemEntity)) {
                        throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 선택지입니다.");
                    }

                    SurveyOptionEntity optionEntity = findOptionById(ConvertUtil.stringToLong(optionId));
                    SurveyResponseOptionEntity reponseOption = responseOptionSave(new SurveyResponseOptionEntity(reponseItem, optionEntity.getOptionName(), optionEntity.getOptionOrder(), optionEntity.getUseYn()));
                }
            }
        }
    }
}

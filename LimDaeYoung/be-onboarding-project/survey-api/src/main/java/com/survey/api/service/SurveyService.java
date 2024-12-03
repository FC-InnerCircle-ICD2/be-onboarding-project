package com.survey.api.service;

import com.survey.api.constant.CommonConstant;
import com.survey.api.dto.SurveyResponseDto;
import com.survey.api.entity.*;
import com.survey.api.exception.SurveyApiException;
import com.survey.api.form.*;
import com.survey.api.repository.*;
import com.survey.api.util.ConvertUtil;
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

    public SurveyItemEntity findItemByIdAndItemTypeAndSurvey(Long id, String itemType, SurveyEntity survey) {
        return surveyItemRepository.findByIdAndItemTypeAndSurvey(id, itemType, survey);
    }

    public SurveyEntity findSurveyById(Long id) {
        return surveyRepository.findSurveyById(id);
    }


    public SurveyItemEntity findItemById(Long id) {
        return surveyItemRepository.findItemById(id);
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
    public void surveyUpdate(SurveyForm survey) {
        SurveyEntity surveyResult = sureveySave(new SurveyEntity(survey.getId(), survey.getName(), survey.getDescription(), CommonConstant.Y));

        if(survey.getItems() != null) {
            for (SurveyItemForm itemForm : survey.getItems()) {
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
                        for (SurveyOptionForm optionForm : itemForm.getOptionList()) {
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

    public List<SurveyResponseEntity> findResponsesBySurveyIdWithFilters(long id, String searchParam, int pageNumber) {
        Pageable pageable = (Pageable) PageRequest.of(pageNumber, 10, Sort.by("id").descending());
        return surveyResponseRepository.findResponsesBySurveyIdWithFilters(id, pageable);
    }

    @Transactional
    public void surveyResponseSave(SurveyForm survey) {
        SurveyEntity surveyEntity = findSurveyById(survey.getId());

        SurveyResponseEntity surveyResult = responseSave(new SurveyResponseEntity(surveyEntity.getId(), surveyEntity.getName(), surveyEntity.getDescription()));

        for (SurveyItemForm itemResponseForm : survey.getItems()) {
            String answer = "";

            if(CommonConstant.SHORT_ITEM.equals(itemResponseForm.getResponseType()) || CommonConstant.LONG_ITEM.equals(itemResponseForm.getResponseType())) {
                answer = (itemResponseForm.getAnswer() != null && itemResponseForm.getAnswer().length > 0 ? itemResponseForm.getAnswer()[0] : "");
            }
            SurveyItemEntity itemEntity = findItemById(itemResponseForm.getId());
            SurveyResponseItemEntity reponseItem = responseItemSave(new SurveyResponseItemEntity(answer, surveyResult, itemEntity.getItemName(),  itemEntity.getDescription(), itemEntity.getItemType(), itemEntity.getUseYn(), itemEntity.isRequired()));

            if(CommonConstant.MULTI_ITEM.equals(itemResponseForm.getResponseType()) || CommonConstant.SINGLE_ITEM.equals(itemResponseForm.getResponseType())) {
                for(String optionId : itemResponseForm.getAnswer()) {
                    SurveyOptionEntity optionEntity = findOptionById(ConvertUtil.stringToLong(optionId));
                    SurveyResponseOptionEntity reponseOption = responseOptionSave(new SurveyResponseOptionEntity(reponseItem, optionEntity.getOptionName(), optionEntity.getOptionOrder(), optionEntity.getUseYn()));
                }
            }
        }
    }

    public boolean itemValidator(List<SurveyItemForm> itemForm) {
        for(SurveyItemForm item : itemForm) {
            SurveyItemEntity itemEntity = findItemByIdAndItemTypeAndSurvey(item.getId(), item.getResponseType(), new SurveyEntity(item.getId()));

            if (itemEntity == null) {
                throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 설문 조사 항목에 대해 응답하였습니다.");
            }

            if (CommonConstant.SINGLE_ITEM.equals(item.getResponseType())
                    || CommonConstant.MULTI_ITEM.equals(item.getResponseType())) {
                String[] optionList = item.getAnswer();
                for (String optionId : optionList) {
                    if (existsSurveyOptionByIdAndItemId(ConvertUtil.stringToLong(optionId), itemEntity)) {
                        throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 선택지를 선택하였습니다.");
                    }
                }
            }
        }


        return true;
    }
}

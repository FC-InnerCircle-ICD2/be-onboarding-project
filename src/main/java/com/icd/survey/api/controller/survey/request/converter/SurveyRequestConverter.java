package com.icd.survey.api.controller.survey.request.converter;

import com.icd.survey.api.controller.survey.request.CreateSurveyRequest;
import com.icd.survey.api.service.survey.command.CreateSurveyCommand;
import com.icd.survey.api.service.survey.command.ItemOptionCommand;
import com.icd.survey.api.service.survey.command.SurveyItemCommand;
import com.icd.survey.common.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class SurveyRequestConverter {

    public static CreateSurveyCommand createSurveyRequestConvert(CreateSurveyRequest request) {
        List<SurveyItemCommand> itemCommandList = new ArrayList<>();

        request.getSurveyItemList().forEach(x -> {
            List<ItemOptionCommand> optionCommandList = new ArrayList<>();
            if (x.isChoiceType()) {
                x.getOptionList().forEach(y -> optionCommandList.add(new ItemOptionCommand(y.getOption())));
            }
            itemCommandList.add(new SurveyItemCommand(x.getItemName(), x.getItemDescription(), x.getIsEssential(), optionCommandList));
        });

        return new CreateSurveyCommand(request.getSurveyName(), request.getSurveyDescription(), CommonUtils.getRequestIp(), itemCommandList);
    }
}

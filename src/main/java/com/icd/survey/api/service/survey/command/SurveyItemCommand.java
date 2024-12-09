package com.icd.survey.api.service.survey.command;

import java.util.List;

public record SurveyItemCommand(
        String itemName,
        String itemDescription,
        Boolean isEssential,
        List<ItemOptionCommand> optionList
) {
}
